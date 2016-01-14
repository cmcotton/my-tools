/*
 * 版權宣告: FDC all rights reserved.
 */
package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

import db.DBConnection;
import db.DBConnectionNative;
import entity.Event;
import errorhandle.MyCEFParsingException;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：TestCEFParsor.java
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


public class TestCEFParsor {

    CEFParser parser = new CEFParser();

    @Test
    public void testParserPass() {
        try {
            InputStream fis = new FileInputStream("d:/log2.txt");

            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line;
            
            DBConnection dbConnection = new DBConnectionNative();
            dbConnection.connect();
            while ((line = br.readLine()) != null) {
                List<Event> evts = null;
                try {
                    
                    evts = parser.parse(line);
                    
                } catch(MyCEFParsingException e) {
                    
                    parser.handleParsingError(line);
                    
                }
                
                
                for (Event e : evts) {
                    
                    dbConnection.save(e);
                }
            }
            
            dbConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testParserException() {
        try {
            InputStream fis = new FileInputStream("d:/logtest.txt");

            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line;
            
            DBConnection dbConnection = new DBConnectionNative();
            dbConnection.connect();
            while ((line = br.readLine()) != null) {
                List<Event> evts = parser.parse(line);
                
                for (Event e : evts) {
                    System.out.println(e.toString());
                    dbConnection.save(e);
                }
            }
            
            dbConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
