/*
 * 版權宣告: FDC all rights reserved.
 */
package entry;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import parser.CEFPostProcessor;
import server.TelnetServer;

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
    
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.out.println("1: start server. 2: resend exception logs");
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
        }
        
    }

}