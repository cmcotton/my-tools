/*
 * 版權宣告: FDC all rights reserved.
 */
package entry;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parser.CEFPostProcessor;
import server.TelnetServer;
import backup.Cleaner;
import backup.ExportManager;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：MainAllProcess.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class MainAllProcess {
    
    static String START_SERVER = "1";
    static String RESEND_EXCEPTION_LOG = "2";
    static String EXPORT_LOG = "4";
    
    private static Logger logger = LoggerFactory.getLogger("MainAllProcess");
    
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.out.println("1: start server. 2: resend exception logs. 4: export data before {n} days.");
            return;
        }
        
        if (START_SERVER.equals(args[0])) {
            TelnetServer server = new TelnetServer();
            try {
                server.run();
            } catch (CertificateException | SSLException | InterruptedException e) {
                e.printStackTrace();
            }
        } else if (RESEND_EXCEPTION_LOG.equals(args[0])) {
            CEFPostProcessor postProcessor = new CEFPostProcessor();
            postProcessor.run();
        } else if (EXPORT_LOG.equals(args[0])) {
            logger.info("EXPORT_LOG begin!!");
            
            int daysToDel = 45; // default 45 days
            
            if (args.length > 1) {
                try {
                    daysToDel = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    
                }
            }
            
            ExportManager exportMgr = new ExportManager();
            exportMgr.export(daysToDel);
            
            Cleaner c = new Cleaner();
            c.deleteOldLog(daysToDel);
            
            logger.info("EXPORT_LOG finished!!");
        } else {
            System.out.println("1: start server. 2: resend exception logs. 4: export data before {n} days.");
            return;
        }
        
        
    }

}