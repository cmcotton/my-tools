/*
 * 版權宣告: FDC all rights reserved.
 */
package backup;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DBConnectionNative;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Cleaner.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public class Cleaner {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public void deleteOldLog(int day) {
        DBConnectionNative db = new DBConnectionNative();
        try {
            db.connect();
            db.deleteOldData(day);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
        } finally {
            db.close();
        }
        
        
    }
}
