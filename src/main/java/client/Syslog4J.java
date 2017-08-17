/*
 * 版權宣告: FDC all rights reserved.
 */
package client;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Syslog4J.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public class Syslog4J {
    public static void main(String[] args) {
     // Set a Specific Host, then Log to It
        SyslogIF syslog = Syslog.getInstance("tcp");
        syslog.getConfig().setHost("127.0.0.1");
        syslog.getConfig().setPort(8023);
        syslog.info("Log Message");
    }

}
