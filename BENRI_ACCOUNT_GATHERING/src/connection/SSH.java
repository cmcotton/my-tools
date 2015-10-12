package connection;

import io.PropertiesAccessObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class SSH {
    
    final private String CONFIG_FILE = "config.properties";
    final private String MESSAGE_FILE = "message_zh.properties";
    final private String sourceFileKey = "source_file";
    final private String outputFileKey = "output_file";
    final private String serverParamKey = "server_param"; //account@hostname@poart@password;
    
    private ChannelSftp mSftp = null;
    private Session mSession = null;
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 
     * @return boolean 
     */
    public boolean connectRemoteServers() {
        boolean success = false;
        PropertiesAccessObject pao = new PropertiesAccessObject();
        String serversParam = pao.getProperties(CONFIG_FILE, serverParamKey);
        String[] serverParamArray = serversParam.split(";");
        
        logger.info("length:{}", serverParamArray.length);
        
        for(String serverParam : serverParamArray) {
            String[] token = serverParam.split("\\|");
            String host = token[1];
            logger.info(host);
            String strPort = token[2];                
            logger.debug(">>>strPort {}", strPort);
            int port = Integer.parseInt(strPort);
            String username = token[0];
            logger.debug(username);
            String advParam = token[3];
    
            try {
                success = connect(host, port, username, advParam);
                
                logger.info("log in status: {}", success);
                
                downloadSingleFile("/etc/", "passwd", "./source/" + host);
                downloadSingleFile("/etc/", "group", "./source/" + host);
            } catch (JSchException e) {            
                logger.error(e.toString());
            } finally {
                disconnect();
            }
        }        
        return success;        
    }
    
    /**
     * 
     * @param host ip address
     * @param port 
     * @param username Linux username
     * @param advParam 
     * @return success if Channel has been established.
     * @throws JSchException 
     */
    private boolean connect(String host, int port, String username, String advParam) throws JSchException {
        logger.debug(">>>host {}", host);
        logger.debug(">>>port {}", port);
        boolean success = false; 
        JSch jsch = new JSch();
        mSession = jsch.getSession(username, host, port);
        logger.debug(">>>advparam {}", advParam);
        mSession.setPassword(advParam);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        mSession.setConfig(sshConfig);
        mSession.connect();
        Channel channel = mSession.openChannel("sftp");
        channel.connect();
        mSftp = (ChannelSftp) channel;
        
        if (mSftp != null) {
            success = true;
        }
        
        return success;
    }    
    
    /**
     * 
     * @param dir remote Directory
     * @param remoteFile 
     * @param localPath 
     */
    public void download(String dir, String remoteFile, String localPath) {
        if (mSftp == null) {            
            return;
        }
        
        try {
            logger.info(">>>remote dir {}, file: {}", dir, remoteFile);
            logger.debug(">>>localDir {}",  localPath);
            mSftp.cd(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }              
        
        Vector<LsEntry> fileList = new Vector<LsEntry>();
        
        try {
            fileList = mSftp.ls(dir);
        } catch (SftpException e1) {
            logger.error(e1.getMessage());
        }
        
        createLocalDirectory(localPath);
        
        for (int i = 0; i < fileList.size(); i++) {
            String fileName = fileList.get(i).getFilename();
            logger.debug(">>>fileName {}: {}", i, fileName);

            File file = new File(localPath + "/" + fileName);
            if (file.isDirectory()) {
                continue;
            }
            
            try {
                FileOutputStream os = new FileOutputStream(file);
                mSftp.get(dir + "/" + fileName, os);
                os.close();
            } catch (FileNotFoundException e) {
                logger.error(e.toString());
            } catch (SftpException e) {
                logger.error(e.toString());
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }
    
    /**
     * 指定遠端檔案remoteFile 只下載單一個檔案
     * @param dir remote Directory
     * @param remoteFile 
     * @param localPath 
     */
    public void downloadSingleFile(String dir, String remoteFile, String localPath) {
        if (mSftp == null) {            
            return;
        }
        
        try {
            logger.info(">>>remote dir {}, file: {}", dir, remoteFile);
//            logger.debug(">>>remoteFile {}", remoteFile);
            logger.debug(">>>localDir {}", localPath);
            mSftp.cd(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }              
        
        Vector<LsEntry> fileList = new Vector<LsEntry>();
        try {
            fileList = mSftp.ls(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }
        
        createLocalDirectory(localPath);
        try {
            File file = new File(localPath + File.separator + remoteFile);
            FileOutputStream os = new FileOutputStream(file);
            mSftp.get(dir + "/" + remoteFile, os);
            os.close();
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } catch (SftpException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        
    }
    
    /**
     * 
     * @param dir remote Directory
     * @param remoteFile 
     * @param localPath 
     * @param fileType 要下載的檔案內容 
     */
    public void download(String dir, String remoteFile, String localPath, String fileType) {
        if (mSftp == null) {            
            return;
        }
        
        try {
            logger.debug(">>>dir {}", dir);
            logger.debug(">>>remoteFile {}", remoteFile);
            logger.debug(">>>localDir {}", localPath);
            mSftp.cd(dir);
        } catch (SftpException e1) {
            logger.error(e1.getMessage());
        }
        
        Vector<LsEntry> fileList = new Vector<LsEntry>();
        try {
            fileList = mSftp.ls(dir);
        } catch (SftpException e1) {
            logger.error(e1.getMessage());
        }
        
        // 用Set將檔名排序
        Set<String> set = new TreeSet();
        for (int i = 0; i < fileList.size(); i++) {            
            String fileName = fileList.get(i).getFilename();
            if (!".".equals(fileName) && !"..".equals(fileName) && !"backup".equals(fileName) &&
                    fileName.contains(remoteFile)) { 
                set.add(fileName);
            }
        }
        
        createLocalDirectory(localPath);
        
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String fileName = (String) it.next();
            //String fileName = fileList.get(i).getFilename();
            logger.debug(">>>fileName {}", fileName);

            File file = new File(localPath + File.separator + fileName);
            
            // 開始下載檔案
            try {
                FileOutputStream os = new FileOutputStream(file);                
                mSftp.get(dir + "/" + fileName, os); // 強制用/  因為在windows會變成\ 
                os.close();
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
            } catch (SftpException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

        } // end for
    }
    
    /**
     * 
     * @param dir remote Directory
     * @param pattern  
     * @param localPath 
     * 
     */
    public void downloadByFileNamePattern(String dir, String pattern, String localPath) {
        if (mSftp == null) {            
            return;
        }
        
        try {
            logger.debug(">>>dir {}", dir);
            logger.debug(">>>pattern {}", pattern);
            logger.debug(">>>localDir {}", localPath);
            mSftp.cd(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }
        
        Vector<LsEntry> fileList = new Vector<LsEntry>();
        try {
            fileList = mSftp.ls(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }
        
        // 用Set將檔名排序
        Set<String> set = new TreeSet<String>();
        for (int i = 0; i < fileList.size(); i++) {            
            String fileName = fileList.get(i).getFilename();
            if (!".".equals(fileName) && !"..".equals(fileName) && !"backup".equals(fileName) &&
                    fileName.contains(pattern)) { 
                set.add(fileName);
            }
        }
        
        createLocalDirectory(localPath);
        
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String fileName = (String) it.next();
            //String fileName = fileList.get(i).getFilename();
            logger.debug(">>>fileName {}", fileName);

            File file = new File(localPath + File.separator + fileName);
            
            // 開始下載檔案
            try {
                FileOutputStream os = new FileOutputStream(file);                
                mSftp.get(dir + "/" + fileName, os); // 強制用/  因為在windows會變成\ 
                os.close();
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
            } catch (SftpException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

        } // end for
    }
    
    
    /**
     * 下載檔案檔名大於baseFile的以及下載的檔案個數不超過itemNumLimit 
     * @param dir 
     * @param localPath 
     * @param baseFile 
     * @param itemNumLimit (0為不限制) 
     * @return  Set<File> 
     */
    public Set<File> downloadByFNPatternAndBaseFile(String dir, String localPath, 
                String baseFile, int itemNumLimit) {
        Set<File> resultList = new TreeSet<File>();
        if (mSftp == null) {            
            return resultList;
        }
        
        try {
            logger.debug(">>>dir {}", dir);
            logger.debug(">>>baseFile {}", baseFile);
            logger.debug(">>>localDir {}", localPath);
            logger.debug(">>>itemNumLimit {}", itemNumLimit);
            mSftp.cd(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }
        
        Vector<LsEntry> fileList = new Vector<LsEntry>();
        try {
            fileList = mSftp.ls(dir);
        } catch (SftpException e1) {
            logger.error(e1.toString());
        }
        
        
        // 用Set將檔名排序
        Set<String> set = new TreeSet<String>();
        for (int i = 0; i < fileList.size(); i++) {            
            String fileName = fileList.get(i).getFilename();
            if (!".".equals(fileName) && !"..".equals(fileName) && !"backup".equals(fileName) &&
                    fileName.compareToIgnoreCase(baseFile) > 0 && !fileName.toLowerCase().endsWith("ok")) { 
                set.add(fileName);
            }
        }
        
        createLocalDirectory(localPath);
        
        Iterator<String> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            String fileName = (String) it.next();
            //String fileName = fileList.get(i).getFilename();
            logger.debug(">>>fileName {}", fileName);

            File file = new File(localPath + File.separator + fileName);
            
            // 開始下載檔案
            try {
                FileOutputStream os = new FileOutputStream(file);
                resultList.add(file);
                mSftp.get(dir + "/" + fileName, os); // 強制用/  因為在windows會變成\ 
                os.close();
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
            } catch (SftpException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            i++;
            if (itemNumLimit != 0 && i >= itemNumLimit) {
                break;
            }

        } // end for
        return resultList;
    }
    
    public void disconnect() {
        if (mSession != null) {
            mSession.disconnect();
            mSession = null;        
        }
        if (mSftp != null) {
            mSftp.disconnect();
            mSftp = null;
        }                
    }
    
    
    private boolean createLocalDirectory(String localPath) {
        boolean success = false;
        // Create local directory
        File localDir = new File(localPath);
        if (!localDir.exists()) {
            success = localDir.mkdirs();
        }              
        return success;
    }
    
}