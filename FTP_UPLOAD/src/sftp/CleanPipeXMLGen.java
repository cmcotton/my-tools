package sftp;
// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 2015/6/11 下午 01:50:35
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CleanPipeXMLGen.java

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class CleanPipeXMLGen {

    public CleanPipeXMLGen(String args[]) {
        dirPrefix = "/export/home/CleanPipeForward/";
        ftpID = "ftp_ips";
        ftpPWD = "";
        serverName = "172.17.2.100";
        debugMode = "1";
        loadConfig();        
    }

    private void loadConfig() {
        Properties p = new Properties();
        try {
            File f = new File("./config.properties");
            FileInputStream fis = new FileInputStream(f);
            p.load(fis);
            dirPrefix = p.getProperty("dirPrefix");
            serverName = p.getProperty("ftpIP");
            ftpPort = p.getProperty("ftpPort");
            ftpID = p.getProperty("ftpID");
            ftpPWD = p.getProperty("ftpPWD");
            debugMode = p.getProperty("debugMode");
            if (debugMode.equals("1")) {
                System.out.println("DebugInfo: **********************************");
                System.out.println((new StringBuilder()).append("DebugInfo: ").append(dirPrefix).toString());
                System.out.println((new StringBuilder()).append("DebugInfo: ").append(serverName).toString());
                System.out.println((new StringBuilder()).append("DebugInfo: ").append(ftpPort).toString());
                System.out.println((new StringBuilder()).append("DebugInfo: ").append(ftpID).toString());
                System.out.println((new StringBuilder()).append("DebugInfo: ").append(ftpPWD).toString());
                System.out.println("DebugInfo: **********************************");
            }
            
            fileDir = (new StringBuilder()).append(dirPrefix).append("/xml/").toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(-1);
        }
    }

    private String replaceXMLTemplate(String xml, String parameter[]) {
        String xmlString = null;

        xmlString = new String(xml.getBytes());
        xmlString = xmlString.replaceFirst("replace_ArcSightID", parameter[1].trim());
        xmlString = xmlString.replaceFirst("replace_AnalyzerIP", parameter[2].trim());
        String dateString = getDateString(parameter[3].trim());
        xmlString = xmlString.replaceFirst("replace_CreateTime", dateString);
        xmlString = xmlString.replaceFirst("replace_DetectTime", dateString);
        xmlString = xmlString.replaceFirst("replace_AnalyzerTime", dateString);
        xmlString = xmlString.replaceFirst("replace_SourceLocation", parameter[4].trim());
        xmlString = xmlString.replaceFirst("replace_SourceHostName", parameter[5].trim());
        xmlString = xmlString.replaceFirst("replace_SourceIP", parameter[6].trim());
        xmlString = xmlString.replaceFirst("replace_DestinationLocation", parameter[7].trim());
        xmlString = xmlString.replaceFirst("replace_DestinationHostName", parameter[8].trim());
        xmlString = xmlString.replaceFirst("replace_DestinationIP", parameter[9].trim());
        xmlString = xmlString.replaceAll("replace_RuleName", parameter[10].trim());
        xmlString = xmlString.replaceFirst("replace_RuleReference", "");
        if (parameter[0].equals("NotifyAuto")) {
            System.out.println("NotifyAuto");
            if (parameter[8].length() > 0 && parameter[5].length() == 0) {
                System.out.println("NotifyAuto:target");
                int i = parameter[8].indexOf(" ");
                String hn = parameter[8].substring(0, i);
                xmlString = xmlString.replaceFirst("replace_CustomerHN", hn);
                xmlString = xmlString.replaceFirst("replace_CustomerMember", "");
            }

            if (parameter[5].length() > 0) {
                System.out.println("NotifyAuto:attacker");
                int i = parameter[5].indexOf(" ");
                String hn = parameter[5].substring(0, i);
                xmlString = xmlString.replaceFirst("replace_CustomerHN", hn);
                xmlString = xmlString.replaceFirst("replace_CustomerMember", "");
            } else {
                System.out.println("NotifyAuto:none");
                xmlString = xmlString.replaceFirst("replace_CustomerHN", "");
                xmlString = xmlString.replaceFirst("replace_CustomerMember", "");
            }
        } else if (parameter[0].equals("NotifySource")) {
            System.out.println("NotifySource");
            int i = parameter[5].indexOf(" ");
            if (i < 0) {
                xmlString = xmlString.replaceFirst("replace_CustomerHN", parameter[5]);
            } else {
                String hn = parameter[5].substring(0, i);
                xmlString = xmlString.replaceFirst("replace_CustomerHN", hn);
            }
            xmlString = xmlString.replaceFirst("replace_CustomerMember", "");
        } else if (parameter[0].equals("NotifyDestination")) {
            System.out.println("NotifyDestination");
            int i = parameter[8].indexOf(" ");
            if (i < 0) {
                xmlString = xmlString.replaceFirst("replace_CustomerHN", parameter[8]);
            } else {
                String hn = parameter[8].substring(0, i);
                xmlString = xmlString.replaceFirst("replace_CustomerHN", hn);
            }
            xmlString = xmlString.replaceFirst("replace_CustomerMember", "");
        } else {
            System.out.println("NotifyNone");
            xmlString = xmlString.replaceFirst("replace_CustomerHN", "");
            xmlString = xmlString.replaceFirst("replace_CustomerMember", "");
        }
        xmlString = xmlString.replaceFirst("replace_RulePriority", parameter[12].trim());
        xmlString = xmlString.replaceFirst("replace_RuleDescription", parameter[13].trim());
        int priority = 0;
        String risk = "info";
        try {
            priority = Integer.parseInt(parameter[12].trim());
            if (priority >= 0 && priority <= 2)
                risk = "info";
            else if (priority >= 3 && priority <= 4)
                risk = "low";
            else if (priority >= 5 && priority <= 6)
                risk = "medium";
            else if (priority >= 7 && priority <= 10)
                risk = "high";
        } catch (Exception e) {
            e.printStackTrace();
        }
        xmlString = xmlString.replaceFirst("replace_RuleRisk", risk);
        xmlString = xmlString.replaceFirst("replace_RuleAction", parameter[15].trim());
        if (parameter[16].equals("n/a")) {
            xmlString = xmlString.replaceFirst("replace_RuleConfidence", "medium");
        } else {
            xmlString = xmlString.replaceFirst("replace_RuleConfidence", parameter[16].trim().toLowerCase());
            xmlString = xmlString.replaceFirst("replace_OriginalRuleName", parameter[17].trim());
            xmlString = xmlString.replaceFirst("replace_SourcePort", parameter[18].trim());
            xmlString = xmlString.replaceFirst("replace_DestinationPort", parameter[19].trim());
            xmlString = xmlString.replaceFirst("replace_times", parameter[20].trim());
            xmlString = xmlString.replaceFirst("replace_classtech", parameter[21].trim());
            xmlString = xmlString.replaceFirst("replace_reserved1", parameter[22].trim());
            xmlString = xmlString.replaceFirst("replace_reserved2", parameter[23].trim());
        }
        
        String fileName = (new StringBuilder()).append(getDateString(System.currentTimeMillis())).append("_")
                .append(parameter[1].trim()).append(".xml").toString();
        writeToFile((new StringBuilder()).append(fileDir).append(fileName).toString(), xmlString);
//        ftpToSOC365(dir, fileName);
//        upload(dir, fileName);
        return xmlString;

    }

    private String getDateString(long l) {
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
        String s = sf.format(new Date(l));
        return s;
    }

    private String getDateString(String vGrantdate) {
        String finalString = null;
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
        Date date = null;
        try {
            date = dateFormat.parse(vGrantdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date.getTime());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.0-00:0");
        finalString = sf.format(date);
        return finalString;

    }

    private void writeToFile(String fileName, String content) {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));
            System.out.println((new StringBuilder()).append("Length = ").append(content.length()).toString());
            dos.write(content.getBytes());
            dos.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private String loadXMLTemplate() {
        String xmlString = null;
        try {
            File f = new File((new StringBuilder()).append(dirPrefix).append("/template/IDMEF_Template.xml").toString());
            int l = (int) f.length();
            byte buffer[] = new byte[l];
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            dis.readFully(buffer);
            xmlString = new String(buffer);
            System.out.println((new StringBuilder()).append("XML Length = ").append(xmlString.length()).toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return xmlString;
    }

    public void upload(String fileName) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(ftpID, serverName, Integer.parseInt(ftpPort));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(ftpPWD);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            
            // start uploading
            try {
                sftpChannel.put(new FileInputStream(fileDir + fileName), fileName, ChannelSftp.OVERWRITE);
                sftpChannel.put((new StringBuilder()).append(dirPrefix).append("/template/finish.ok").toString(), (new StringBuilder()).append(fileName).append(".ok").toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
            
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();  
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        System.out.println("start");
        if (args.length < 1) {
            System.out.println("1: write XML 2: sftp");
            return;
        }
        
        CleanPipeXMLGen class1 = new CleanPipeXMLGen(args);
        
        if ("1".equals(args[0])) {
            String xmlOriginString = class1.loadXMLTemplate();
            String sb = class1.processSb(args);
            String[] str2 = class1.processStr2(sb);
            class1.replaceXMLTemplate(xmlOriginString, str2);
        } else if ("2".equals(args[0])) {
            File[] xmlFiles = class1.getFiles();
            for (File xmlF : xmlFiles) {
                if (xmlF.isFile()) {
                    class1.upload(xmlF.getName());
                }
            }
        }
        
    }

    private String[] processStr2(String sb) {
        System.out.println((new StringBuilder()).append("Receive Parameter : ").append(sb.toString()).toString());
        String str2[] = sb.split("\\|");
        System.out.println("=======================================================");
        for (int i = 0; i < str2.length; i++) {
            if (str2[i].startsWith("$")) {
                str2[i] = "";
            }
            System.out.println((new StringBuilder()).append("Parameter ").append(i).append(" : ").append(str2[i])
                    .append(" : Length = ").append(str2[i].length()).toString());
        }

        System.out.println("=======================================================");
        
        return str2;
    }
    
    private String processSb(String[] args) {
        String sb = new String();
        int i = 0;
        for (i = 0; i < args.length; i++) {
            sb = (new StringBuilder()).append(sb).append(args[i]).append(" ").toString();
        }
        
        return sb;
    }
    
    private File[] getFiles() {
        File xmlFileDir = new File(fileDir);        
        File[] xmlFiles = xmlFileDir.listFiles();
        return xmlFiles;
    }
    
    private String dirPrefix;
    private String ftpID;
    private String ftpPWD;
    private String serverName;
    private String ftpPort;
    private String debugMode;
    private String fileDir;
}
