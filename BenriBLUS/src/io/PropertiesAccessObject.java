package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class PropertiesAccessObject {
    
    
    /**
     * 取得系統參數設定值.
     *
     * @param propName 要讀取的properties檔名
     * @param key 要讀取的參數
     * @return value 參數值     
     */
    public String getProperties(String propName, String key) {
        String value = "";
        
        boolean flag = false;
        if (key.toLowerCase().indexOf("adv") >= 0 && key.toLowerCase().indexOf("param") >= 0) {
            flag = true;
        }
        
        
        Properties prop = loadPropertiesFile(propName);
        
        // 預先檢查要查詢的key是否存在
        if (prop.containsKey(key)) {
            value = prop.getProperty(key);
            
        } else {            
            System.out.println("ERROR! Key " + key + " is NOT FOUND!");
        }
        
        return value;
    }
    
//    public synchronized boolean setProperties(String propName, String key, String value) {
//        
//        boolean flag = false;
//        // 本系統所有密碼的key都包含adv及param字串
//        if (key.toLowerCase().indexOf("adv") >= 0 && key.toLowerCase().indexOf("param") >= 0) {
//            flag = true;
//        }
//        
//        
//        Properties prop = loadPropertiesFile(propName);
//        //2011/11/17 add by CHUNTACHEN begin
//        if (flag) {
//            //密碼要加密後才能寫入設定檔
//            try {
//                value = encode(genKey(), value);
//            } catch (Exception ex) {
//                logger.error(ex.getMessage() + " encode password error");
//            }
//        }
//        //2011/11/17 add by CHUNTACHEN end
//        prop.setProperty(key, value);
//        logger.debug(">>>" + key + " " + value);
//        // 將設定值寫入檔案                          
//        return savePropertiesFile(propName, prop);
//    }
//    
    private Properties loadPropertiesFile(String propName) {
              
        Properties prop = new Properties();
        FileInputStream inputFile;
        
        // 讀取properties檔案, load後把檔案關閉
        try {                       
            inputFile = new FileInputStream(propName);
            prop.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return prop;
    }
    
//    private boolean savePropertiesFile(String propName, Properties prop) {
//        
//        boolean success = false;     
//        
//        FileOutputStream outputFile;
//        
//        // 讀取properties檔案, load後把檔案關閉
//        try {                       
//            outputFile = new FileOutputStream(mPath + "/" + propName);
//            prop.store(outputFile, "yms-sysconfig");          
//            outputFile.close();
//            success = true;
//        } catch (FileNotFoundException e) {
//            logger.error(e.getMessage());
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//        
//        return success;
//    }
// 
    

    
}
