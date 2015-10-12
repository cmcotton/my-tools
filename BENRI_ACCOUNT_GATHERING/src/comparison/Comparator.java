/*
 * 版權宣告: FDC all rights reserved.
 */
package comparison;

import io.FileIO;
import io.PropertiesAccessObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import excel.ExcelDataReaderXLS;
import excel.ExcelDataReaderXLSX;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：Comparator.java
 * <P>
 * 程式內容說明：
 * <P>
 * 程式修改記錄：
 * <P>
 * XXXX-XX-XX：
 * <P>
 * 
 * @author chtd
 * @version 1.0
 * @since 1.0
 */
public class Comparator {

    final private String CONFIG_FILE = "config.properties";
    final private String MESSAGE_FILE = "message_zh.properties";
    final private String sourceFileKey = "source_file";
    final private String outputFileKey = "output_file";
    
    Logger logger = LoggerFactory.getLogger(getClass());

    String sourcePath;

    public Comparator() {
        PropertiesAccessObject pao = new PropertiesAccessObject();
        sourcePath = pao.getProperties(CONFIG_FILE, sourceFileKey);
    }

    public void findNewAccount() {

        FileIO io = new FileIO();
        File srcDir = new File(sourcePath);

        File[] allServerDir = srcDir.listFiles();

        if (allServerDir == null) {
            return;
        }

        for (File serverDir : allServerDir) {
            File[] allFiles = serverDir.listFiles();

            for (File file : allFiles) {
                if (file.getName().contains("pass")) {
                    List<String> accountList = io.readFileIntoList(file);
                    
                    // output 新帳號以及已經不存在的帳號
                    compareAccountAgainstExcel(accountList, serverDir.getName());
                

                    // output 與excel檔上紀錄的群組不同的帳號
                    
                    compareGroupAgainstExcel(accountList, serverDir.getName());
                    
                }
            }
        }

    }

    public void findRemovedAccount() {

    }
    
    private void compareAccountAgainstExcel(List<String> accountList, String iPAddr) {
        ExcelDataReaderXLS reader = new ExcelDataReaderXLSX();
        File lastSummaryTable = new File("./SOP系統管理資訊表.xlsx");
        Map<String, String> ipHostNameMap = reader.extractTwoColumn(lastSummaryTable, "主機IP清單", 7, 2);
        String targetHotName = ipHostNameMap.get(iPAddr);
        
        File newAccountFile = new File("./output/newAccount.txt");
        FileIO io = new FileIO();
        
        Map<Integer, String> whereCon = new HashMap<>();
        whereCon.put(new Integer(1), targetHotName);
        whereCon.put(new Integer(7), "Y"); // 使用中
        List<String> enrolledAccount = reader.extractSingleColumn(lastSummaryTable, "帳號審查", 3, whereCon);
        
        for (String accountString : accountList) {
            String account = accountString.split(":")[0];
            if (!enrolledAccount.contains(account)) {
                io.writefile(newAccountFile, account + "," + iPAddr + "\n");
            }
        }
        
        File removedAccountFile = new File("./output/removedAccount.txt");
        for (String accountString : enrolledAccount) {
            
            if (!isInList(accountString, accountList)) {
                io.writefile(removedAccountFile, accountString + "," + iPAddr + "\n");
            }
        }
        
        logger.info("Write {} at {}", newAccountFile.getName(), newAccountFile.getPath());
        logger.info("Write {} at {}", removedAccountFile.getName(), removedAccountFile.getPath());
    }
    
    private void compareGroupAgainstExcel(List<String> accountList, String iPAddr) {
        ExcelDataReaderXLS reader = new ExcelDataReaderXLSX();
        File lastSummaryTable = new File("./SOP系統管理資訊表.xlsx");
        Map<String, String> ipHostNameMap = reader.extractTwoColumn(lastSummaryTable, "主機IP清單", 7, 2);
        String targetHotName = ipHostNameMap.get(iPAddr);
        
        File groupChanged = new File("./output/groupChanged.txt");
        FileIO io = new FileIO();
        List<String> groupList = io.readFileIntoList(new File("./source/" + iPAddr + "/group"));
        
        Map<Integer, String> whereCon = new HashMap<>();
        whereCon.put(new Integer(1), targetHotName);
        whereCon.put(new Integer(7), "Y"); // 使用中
        Map<String, String> accountGroupMap = reader.extractTwoColumn(lastSummaryTable, "帳號審查", 3, 4, whereCon);
        
        // 換算成group name       
        
        for (String accountString : groupList) {
            String account = accountString.split(":")[0];
            String accountGroupKey = accountString.split(":")[2];            
            
            String groupName = getGroupName(groupList, accountGroupKey);

            String formerGroup = accountGroupMap.get(account);
            
            if (!groupName.equals(formerGroup)) {
                io.writefile(groupChanged, account + "," + iPAddr + "\n");
            }
        }
        
        logger.info("Write {} at {}", groupChanged.getName(), groupChanged.getPath());
        
    }
    
    private boolean isInList(String s, List<String> baseList) {
        boolean isMatched = false;
        
        for (String baseString : baseList) {
            
            String baseAccount = baseString.split(":")[0];
            
            if (s.equals(baseAccount)) {
                isMatched = true;
                break;
            }
        }
        
        return isMatched;
    }

    private String getGroupName(List<String> groupList, String key) {
        String groupName = "";
        
        for (String s : groupList) {
            String[] token = s.split(":");
            if (token[2].equals(key)) {
                groupName = token[0];
                break;
            }
        }
        
        return groupName;
    }
}
