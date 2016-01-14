/*
 * 版權宣告: FDC all rights reserved.
 */
package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DBConnection;
import db.DBConnectionNative;
import entity.ExceptionLog;
import errorhandle.MyCEFParsingException;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：CEFPostProcessor.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class CEFPostProcessor {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final CEFParser parser = new CEFParser();
    
    private final DBConnection db = new DBConnectionNative();
    
    public void run() {
        
        try {
            db.connect();
        } catch (FileNotFoundException e) {
            logger.error("{}", e.toString());
        } catch (IOException e) {
            logger.error("{}", e.toString());
        }
        List<ExceptionLog> list = db.getExceptionLog();
        
        for (ExceptionLog el : list) {
            parse(el);
        }
        
    }

    private void parse(ExceptionLog el) {
        try {
            parser.parse(el.getLog());
        } catch (MyCEFParsingException e) {
            handleParsingError(el.getId());
        }
    }
    
    private void handleParsingError(long id) {
        logger.info("update tbale 'exception_log' : {}", id);

        try {
            db.connect();
            db.updateExceptionLog(id);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            db.close();
        }
    }
}
