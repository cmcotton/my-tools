/*
 * 版權宣告: FDC all rights reserved.
 */
package entity;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：EventLog.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */

@Entity
@Table(name = "ARC_EVENT_CORRELATION")
public class ArcEventCorrelation implements Event {
    
    
    private long id;
    
    private String BASE_EVENT_IDS = "";
    
    @Id
    private long CORRELATED_EVENT_ID = 0;
    private String ESM_HOST = "";
    private Date end_time = new Date();
    /**
     * @return the bASE_EVENT_IDS
     */
    public String getBASE_EVENT_IDS() {
        return BASE_EVENT_IDS;
    }
    /**
     * @param bASE_EVENT_IDS the bASE_EVENT_IDS to set
     */
    public void setBASE_EVENT_IDS(String bASE_EVENT_IDS) {
        BASE_EVENT_IDS = bASE_EVENT_IDS;
    }
    /**
     * @return the cORRELATED_EVENT_ID
     */
    public long getCORRELATED_EVENT_ID() {
        return CORRELATED_EVENT_ID;
    }
    /**
     * @param cORRELATED_EVENT_ID the cORRELATED_EVENT_ID to set
     */
    public void setCORRELATED_EVENT_ID(long cORRELATED_EVENT_ID) {
        CORRELATED_EVENT_ID = cORRELATED_EVENT_ID;
    }
    /**
     * @return the eSM_HOST
     */
    public String getESM_HOST() {
        return ESM_HOST;
    }
    /**
     * @param eSM_HOST the eSM_HOST to set
     */
    public void setESM_HOST(String eSM_HOST) {
        ESM_HOST = eSM_HOST;
    }
    /**
     * @return the end_time
     */
    public Date getEnd_time() {
        return end_time;
    }
    /**
     * @param end_time the end_time to set
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
    /* (non-Javadoc)
     * @see entity.Event#setSTART_TIME(java.util.Date)
     */
    public void setSTART_TIME(Date date) {
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see entity.Event#setMANAGER_RECEIPT_TIME(java.util.Date)
     */
    public void setMANAGER_RECEIPT_TIME(Date date) {
        // TODO Auto-generated method stub
        
    }
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
    
    
  
}
