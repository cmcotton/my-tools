/*
 * 版權宣告: FDC all rights reserved.
 */
package entity;

import java.util.Date;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Event.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public interface Event {

    void setSTART_TIME(Date date);
    
    void setMANAGER_RECEIPT_TIME(Date date);
    
    void setEnd_time(Date date);
}
