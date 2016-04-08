/*
 * 版權宣告: FDC all rights reserved.
 */
package backup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DBConnectionNative;
import entity.Event;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Export.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public class ExportManager {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private String DUMP_PATH = "/opt/cefParser/";
    
    /**
     * query data before n days from DB, transform to Json and export a txt named table.yyyyMMdd.txt    
     * @param day
     */
    public void export(int day) {
        DBConnectionNative db = new DBConnectionNative();
        
        try {
            db.connect();
            
            // arc_event
            List<Event> results = db.exportArcEventOldData(day);
            writeFile("arc_event", results);
            
            logger.debug("export arc_event done");
            
            // arc_event_correlation
            List<Event> aecResults = db.exportArcEventCorrelationOldData(day);
            writeFile("arc_event_correlation", aecResults);
            
            logger.debug("export arc_event_correlation done");
            
            // exception_log
            List<Event> excResults = db.exportExceptionLogOldData(day);
            writeFile("exception_log", excResults);
            
            logger.debug("export arc_event_correlation done");
            
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error(e1.toString());
        } finally {
            db.close();
        }
    }
    
    
    private void writeFile(String tableName, List<Event> contents) {
        
        Path dest = Paths.get(DUMP_PATH + tableName + "_" + sdf.format(new Date()) + ".txt");
        try {
            Files.createFile(dest);
        } catch (IOException e) {

        }
        
        for (Event a : contents) {
            JSONObject json = new JSONObject(a);
            try {
                Files.write(dest, (json.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }
    
}
