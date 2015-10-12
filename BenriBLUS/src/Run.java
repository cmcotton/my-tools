import io.PropertiesAccessObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import attachment.AttachmentReceiver;
import convertor.Convertor;
import convertor.ExcelFileConvertor;
import convertor.TextFileConvertor;


public class Run {
    
    final private static String CONFIG_FILE = "config.properties";
    final private static String MESSAGE_FILE = "message_zh.properties";
    final private static String sourceFileKey = "source_file";
    final private static String outputFileKey = "output_file";
    final private static String mailHostKey = "mail_host";
    final private static String mailPortKey = "mail_port";
    final private static String mailIdKey = "mail_user_id";
    final private static String mailPWKey = "mail_pw";
    final private static String mailSubjectKeywordKey = "subject_keyword";
    final private static String filePatternKey = "file_pattern";
    
    
    public void doBLUSFileConvertor() {
        PropertiesAccessObject pao = new PropertiesAccessObject();
        String sourceDirPath = null;
        try {
            sourceDirPath = pao.getProperties(CONFIG_FILE, sourceFileKey);
            System.out.println(sourceDirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sourceDirPath.trim().length() == 0) {
            System.err.println("Source directory was not defined.");
            return;
        }

        File sourceDir = new File(sourceDirPath);
        File[] sourceFiles = sourceDir.listFiles();

        if (sourceFiles.length == 0) {
            System.out.println("no source file.");
            return;
        }
        
        for (File f : sourceFiles) {
            //
            System.out.println("檢視 " + f.getName());
            parseSourceFile(f);
            System.out.println("--------------------------------------------");
        }
    }

    private void parseSourceFile(File oriFile) {
        
        Convertor con = null;
        if (oriFile.getName().endsWith("txt")) {
            con = new TextFileConvertor();
        } else if (oriFile.getName().endsWith("xls")) {
            con = new ExcelFileConvertor();            
        } else {
            con = new ExcelFileConvertor();
        }
        
        if (matchFilePattern(oriFile.getName())) {
            System.out.println("處理中");
            con.deleteOldFile(oriFile);
            con.generateBlusFile(oriFile);
        } else {
            System.out.println("不處理");
        }
        
    }
    
    private boolean matchFilePattern(String fileName) {
        PropertiesAccessObject pao = new PropertiesAccessObject();
        String filePatterns = pao.getProperties(CONFIG_FILE, filePatternKey);
        
        String[] patternArr = filePatterns.split("\\|");
        
        for (String s : patternArr) {
            if (fileName.contains(s)) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        PropertiesAccessObject pao = new PropertiesAccessObject();
        String host = pao.getProperties(CONFIG_FILE, mailHostKey);
        String port = pao.getProperties(CONFIG_FILE, mailPortKey);
        String userId = pao.getProperties(CONFIG_FILE, mailIdKey);
        String pw = pao.getProperties(CONFIG_FILE, mailPWKey);
        String sourceDir = pao.getProperties(CONFIG_FILE, sourceFileKey);
        String keyword = pao.getProperties(CONFIG_FILE, mailSubjectKeywordKey);

        // Download attachment in ./source/
        String[] keywordArr = keyword.split("\\|");
        List<String> keywordList = new ArrayList<>();
        keywordList = Arrays.asList(keywordArr);
        
        AttachmentReceiver re = new AttachmentReceiver();        
        re.setSaveDirectory(sourceDir);
//        re.downloadSOCParsingFiles(host, port, userId, pw, keywordList);
        
        // start to convert
        Run r = new Run();
        r.doBLUSFileConvertor();
    }
}
