/*
 * 版權宣告: FDC all rights reserved.
 */
package entity;

import java.util.Date;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ExceptionLog.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class ExceptionLog {
    
    long id;
    String log;
    Date exceptionTime;
    int retryTime;
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the log
     */
    public String getLog() {
        return log;
    }
    /**
     * @param log the log to set
     */
    public void setLog(String log) {
        this.log = log;
    }
    /**
     * @return the exceptionTime
     */
    public Date getExceptionTime() {
        return exceptionTime;
    }
    /**
     * @param exceptionTime the exceptionTime to set
     */
    public void setExceptionTime(Date exceptionTime) {
        this.exceptionTime = exceptionTime;
    }
    /**
     * @return the retryTime
     */
    public int getRetryTime() {
        return retryTime;
    }
    /**
     * @param retryTime the retryTime to set
     */
    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }
    
    
}
