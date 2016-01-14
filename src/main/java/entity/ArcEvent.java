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
@Table(name = "ARC_EVENT")
public class ArcEvent implements Event {
    
    
    private long id;
    
    private String ESM_HOST = "";
    
    @Id
    private long EVENT_ID;
    private Date START_TIME = new Date();
    private String NAME = "";
    private int PRIORITY = 0;
    private long SRC_ADDRESS = 0;
    private int SRC_PORT;
    private String SOURCE_COUNTRY_NAME = "";
    private long DEST_ADDRESS;
    private int DEST_PORT;
    private String DESC_COUNTRY_NAME = "";
    private String DEST_HOST_NAME = "";
    private int BASE_EVENT_COUNT = 1;
    private Date MANAGER_RECEIPT_TIME = new Date();;
    private String DVC_ACTION = "";
    private String DVC_CUSTOM_STRING1 = "";
    private String FILE_NAME = "";
    private String DVC_PRODUCT = "";
    private String EVENT_TYPE = "";
    private String DVC_CUSTOM_IPV6_ADDRESS1 = "";
    private String DVC_CUSTOM_IPV6_ADDRESS2 = "";
    private String DVC_CUSTOM_IPV6_ADDRESS3 = "";
    private String DVC_CUSTOM_IPV6_ADDRESS4 = "";
    private String DVC_CUSTOM_STRING2 = "";
    private String DVC_CUSTOM_STRING3 = "";
    private String DVC_CUSTOM_STRING4 = "";
    private String DVC_CUSTOM_STRING5 = "";
    private String DVC_CUSTOM_STRING6 = "";
    private String SRC_HOST_NAME = "";
    private String FILE_PATH = "";
    private String DVC_VENDOR = "";
    private String SRC_USER_NAME = "";
    private String DEST_USER_NAME = "";
    private long DVC_ADDRESS = 0;
    private String DVC_HOST_NAME = "";
    private String DVC_PROCESS_NAME = "";
    private String REQUEST_URL = "";
    private String EXTERNAL_ID = "";
    private String DVC_OUTBOUND_INTERFACE = "";
    private String DVC_EVENT_CLASS_ID = "";
    private String CATEGORY_SIGNIFICANCE = "";
    private String CATEGORY_BEHAVIOR = "";
    private String CATEGORY_DEVICE_GROUP = "";
    private String CATEGORY_OUTCOME = "";
    private String CATEGORY_OBJECT = "";

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
     * @return the eVENT_ID
     */
    public long getEVENT_ID() {
        return EVENT_ID;
    }
    /**
     * @param eVENT_ID the eVENT_ID to set
     */
    public void setEVENT_ID(long eVENT_ID) {
        EVENT_ID = eVENT_ID;
    }
    /**
     * @return the sTART_TIME
     */
    public Date getSTART_TIME() {
        return START_TIME;
    }
    /**
     * @param sTART_TIME the sTART_TIME to set
     */
    public void setSTART_TIME(Date sTART_TIME) {
        START_TIME = sTART_TIME;
    }
    /**
     * @return the nAME
     */
    public String getNAME() {
        return NAME;
    }
    /**
     * @param nAME the nAME to set
     */
    public void setNAME(String nAME) {
        NAME = nAME;
    }
    /**
     * @return the pRIORITY
     */
    public int getPRIORITY() {
        return PRIORITY;
    }
    /**
     * @param pRIORITY the pRIORITY to set
     */
    public void setPRIORITY(int pRIORITY) {
        PRIORITY = pRIORITY;
    }
    /**
     * @return the sRC_ADDRESS
     */
    public long getSRC_ADDRESS() {
        return SRC_ADDRESS;
    }
    /**
     * @param sRC_ADDRESS the sRC_ADDRESS to set
     */
    public void setSRC_ADDRESS(long sRC_ADDRESS) {
        SRC_ADDRESS = sRC_ADDRESS;
    }
    /**
     * @return the sRC_PORT
     */
    public int getSRC_PORT() {
        return SRC_PORT;
    }
    /**
     * @param sRC_PORT the sRC_PORT to set
     */
    public void setSRC_PORT(int sRC_PORT) {
        SRC_PORT = sRC_PORT;
    }
    /**
     * @return the sOURCE_COUNTRY_NAME
     */
    public String getSOURCE_COUNTRY_NAME() {
        return SOURCE_COUNTRY_NAME;
    }
    /**
     * @param sOURCE_COUNTRY_NAME the sOURCE_COUNTRY_NAME to set
     */
    public void setSOURCE_COUNTRY_NAME(String sOURCE_COUNTRY_NAME) {
        SOURCE_COUNTRY_NAME = sOURCE_COUNTRY_NAME;
    }
    /**
     * @return the dEST_ADDRESS
     */
    public long getDEST_ADDRESS() {
        return DEST_ADDRESS;
    }
    /**
     * @param dEST_ADDRESS the dEST_ADDRESS to set
     */
    public void setDEST_ADDRESS(long dEST_ADDRESS) {
        DEST_ADDRESS = dEST_ADDRESS;
    }
    /**
     * @return the dEST_PORT
     */
    public int getDEST_PORT() {
        return DEST_PORT;
    }
    /**
     * @param dEST_PORT the dEST_PORT to set
     */
    public void setDEST_PORT(int dEST_PORT) {
        DEST_PORT = dEST_PORT;
    }
    /**
     * @return the dESC_COUNTRY_NAME
     */
    public String getDESC_COUNTRY_NAME() {
        return DESC_COUNTRY_NAME;
    }
    /**
     * @param dESC_COUNTRY_NAME the dESC_COUNTRY_NAME to set
     */
    public void setDESC_COUNTRY_NAME(String dESC_COUNTRY_NAME) {
        DESC_COUNTRY_NAME = dESC_COUNTRY_NAME;
    }
    /**
     * @return the dEST_HOST_NAME
     */
    public String getDEST_HOST_NAME() {
        return DEST_HOST_NAME;
    }
    /**
     * @param dEST_HOST_NAME the dEST_HOST_NAME to set
     */
    public void setDEST_HOST_NAME(String dEST_HOST_NAME) {
        DEST_HOST_NAME = dEST_HOST_NAME;
    }
    /**
     * @return the bASE_EVENT_COUNT
     */
    public int getBASE_EVENT_COUNT() {
        return BASE_EVENT_COUNT;
    }
    /**
     * @param bASE_EVENT_COUNT the bASE_EVENT_COUNT to set
     */
    public void setBASE_EVENT_COUNT(int bASE_EVENT_COUNT) {
        BASE_EVENT_COUNT = bASE_EVENT_COUNT;
    }
    /**
     * @return the mANAGER_RECEIPT_TIME
     */
    public Date getMANAGER_RECEIPT_TIME() {
        return MANAGER_RECEIPT_TIME;
    }
    /**
     * @param mANAGER_RECEIPT_TIME the mANAGER_RECEIPT_TIME to set
     */
    public void setMANAGER_RECEIPT_TIME(Date mANAGER_RECEIPT_TIME) {
        MANAGER_RECEIPT_TIME = mANAGER_RECEIPT_TIME;
    }
    /**
     * @return the dVC_ACTION
     */
    public String getDVC_ACTION() {
        return DVC_ACTION;
    }
    /**
     * @param dVC_ACTION the dVC_ACTION to set
     */
    public void setDVC_ACTION(String dVC_ACTION) {
        DVC_ACTION = dVC_ACTION;
    }
    /**
     * @return the dVC_CUSTOM_STRING1
     */
    public String getDVC_CUSTOM_STRING1() {
        return DVC_CUSTOM_STRING1;
    }
    /**
     * @param dVC_CUSTOM_STRING1 the dVC_CUSTOM_STRING1 to set
     */
    public void setDVC_CUSTOM_STRING1(String dVC_CUSTOM_STRING1) {
        DVC_CUSTOM_STRING1 = dVC_CUSTOM_STRING1;
    }
    /**
     * @return the fILE_NAME
     */
    public String getFILE_NAME() {
        return FILE_NAME;
    }
    /**
     * @param fILE_NAME the fILE_NAME to set
     */
    public void setFILE_NAME(String fILE_NAME) {
        FILE_NAME = fILE_NAME;
    }
    /**
     * @return the dVC_PRODUCT
     */
    public String getDVC_PRODUCT() {
        return DVC_PRODUCT;
    }
    /**
     * @param dVC_PRODUCT the dVC_PRODUCT to set
     */
    public void setDVC_PRODUCT(String dVC_PRODUCT) {
        DVC_PRODUCT = dVC_PRODUCT;
    }
    /**
     * @return the eVENT_TYPE
     */
    public String getEVENT_TYPE() {
        return EVENT_TYPE;
    }
    /**
     * @param eVENT_TYPE the eVENT_TYPE to set
     */
    public void setEVENT_TYPE(String eVENT_TYPE) {
        EVENT_TYPE = eVENT_TYPE;
    }
    /**
     * @return the dVC_CUSTOM_IPV6_ADDRESS1
     */
    public String getDVC_CUSTOM_IPV6_ADDRESS1() {
        return DVC_CUSTOM_IPV6_ADDRESS1;
    }
    /**
     * @param dVC_CUSTOM_IPV6_ADDRESS1 the dVC_CUSTOM_IPV6_ADDRESS1 to set
     */
    public void setDVC_CUSTOM_IPV6_ADDRESS1(String dVC_CUSTOM_IPV6_ADDRESS1) {
        DVC_CUSTOM_IPV6_ADDRESS1 = dVC_CUSTOM_IPV6_ADDRESS1;
    }
    /**
     * @return the dVC_CUSTOM_IPV6_ADDRESS2
     */
    public String getDVC_CUSTOM_IPV6_ADDRESS2() {
        return DVC_CUSTOM_IPV6_ADDRESS2;
    }
    /**
     * @param dVC_CUSTOM_IPV6_ADDRESS2 the dVC_CUSTOM_IPV6_ADDRESS2 to set
     */
    public void setDVC_CUSTOM_IPV6_ADDRESS2(String dVC_CUSTOM_IPV6_ADDRESS2) {
        DVC_CUSTOM_IPV6_ADDRESS2 = dVC_CUSTOM_IPV6_ADDRESS2;
    }
    /**
     * @return the dVC_CUSTOM_IPV6_ADDRESS3
     */
    public String getDVC_CUSTOM_IPV6_ADDRESS3() {
        return DVC_CUSTOM_IPV6_ADDRESS3;
    }
    /**
     * @param dVC_CUSTOM_IPV6_ADDRESS3 the dVC_CUSTOM_IPV6_ADDRESS3 to set
     */
    public void setDVC_CUSTOM_IPV6_ADDRESS3(String dVC_CUSTOM_IPV6_ADDRESS3) {
        DVC_CUSTOM_IPV6_ADDRESS3 = dVC_CUSTOM_IPV6_ADDRESS3;
    }
    /**
     * @return the dVC_CUSTOM_IPV6_ADDRESS4
     */
    public String getDVC_CUSTOM_IPV6_ADDRESS4() {
        return DVC_CUSTOM_IPV6_ADDRESS4;
    }
    /**
     * @param dVC_CUSTOM_IPV6_ADDRESS4 the dVC_CUSTOM_IPV6_ADDRESS4 to set
     */
    public void setDVC_CUSTOM_IPV6_ADDRESS4(String dVC_CUSTOM_IPV6_ADDRESS4) {
        DVC_CUSTOM_IPV6_ADDRESS4 = dVC_CUSTOM_IPV6_ADDRESS4;
    }
    /**
     * @return the dVC_CUSTOM_STRING2
     */
    public String getDVC_CUSTOM_STRING2() {
        return DVC_CUSTOM_STRING2;
    }
    /**
     * @param dVC_CUSTOM_STRING2 the dVC_CUSTOM_STRING2 to set
     */
    public void setDVC_CUSTOM_STRING2(String dVC_CUSTOM_STRING2) {
        DVC_CUSTOM_STRING2 = dVC_CUSTOM_STRING2;
    }
    /**
     * @return the dVC_CUSTOM_STRING3
     */
    public String getDVC_CUSTOM_STRING3() {
        return DVC_CUSTOM_STRING3;
    }
    /**
     * @param dVC_CUSTOM_STRING3 the dVC_CUSTOM_STRING3 to set
     */
    public void setDVC_CUSTOM_STRING3(String dVC_CUSTOM_STRING3) {
        DVC_CUSTOM_STRING3 = dVC_CUSTOM_STRING3;
    }
    /**
     * @return the dVC_CUSTOM_STRING4
     */
    public String getDVC_CUSTOM_STRING4() {
        return DVC_CUSTOM_STRING4;
    }
    /**
     * @param dVC_CUSTOM_STRING4 the dVC_CUSTOM_STRING4 to set
     */
    public void setDVC_CUSTOM_STRING4(String dVC_CUSTOM_STRING4) {
        DVC_CUSTOM_STRING4 = dVC_CUSTOM_STRING4;
    }
    /**
     * @return the dVC_CUSTOM_STRING5
     */
    public String getDVC_CUSTOM_STRING5() {
        return DVC_CUSTOM_STRING5;
    }
    /**
     * @param dVC_CUSTOM_STRING5 the dVC_CUSTOM_STRING5 to set
     */
    public void setDVC_CUSTOM_STRING5(String dVC_CUSTOM_STRING5) {
        DVC_CUSTOM_STRING5 = dVC_CUSTOM_STRING5;
    }
    /**
     * @return the dVC_CUSTOM_STRING6
     */
    public String getDVC_CUSTOM_STRING6() {
        return DVC_CUSTOM_STRING6;
    }
    /**
     * @param dVC_CUSTOM_STRING6 the dVC_CUSTOM_STRING6 to set
     */
    public void setDVC_CUSTOM_STRING6(String dVC_CUSTOM_STRING6) {
        DVC_CUSTOM_STRING6 = dVC_CUSTOM_STRING6;
    }
    /**
     * @return the sRC_HOST_NAME
     */
    public String getSRC_HOST_NAME() {
        return SRC_HOST_NAME;
    }
    /**
     * @param sRC_HOST_NAME the sRC_HOST_NAME to set
     */
    public void setSRC_HOST_NAME(String sRC_HOST_NAME) {
        SRC_HOST_NAME = sRC_HOST_NAME;
    }
    /**
     * @return the fILE_PATH
     */
    public String getFILE_PATH() {
        return FILE_PATH;
    }
    /**
     * @param fILE_PATH the fILE_PATH to set
     */
    public void setFILE_PATH(String fILE_PATH) {
        FILE_PATH = fILE_PATH;
    }
    /**
     * @return the dVC_VENDOR
     */
    public String getDVC_VENDOR() {
        return DVC_VENDOR;
    }
    /**
     * @param dVC_VENDOR the dVC_VENDOR to set
     */
    public void setDVC_VENDOR(String dVC_VENDOR) {
        DVC_VENDOR = dVC_VENDOR;
    }
    /**
     * @return the sRC_USER_NAME
     */
    public String getSRC_USER_NAME() {
        return SRC_USER_NAME;
    }
    /**
     * @param sRC_USER_NAME the sRC_USER_NAME to set
     */
    public void setSRC_USER_NAME(String sRC_USER_NAME) {
        SRC_USER_NAME = sRC_USER_NAME;
    }
    /**
     * @return the dEST_USER_NAME
     */
    public String getDEST_USER_NAME() {
        return DEST_USER_NAME;
    }
    /**
     * @param dEST_USER_NAME the dEST_USER_NAME to set
     */
    public void setDEST_USER_NAME(String dEST_USER_NAME) {
        DEST_USER_NAME = dEST_USER_NAME;
    }
    /**
     * @return the dVC_ADDRESS
     */
    public long getDVC_ADDRESS() {
        return DVC_ADDRESS;
    }
    /**
     * @param dVC_ADDRESS the dVC_ADDRESS to set
     */
    public void setDVC_ADDRESS(long dVC_ADDRESS) {
        DVC_ADDRESS = dVC_ADDRESS;
    }
    /**
     * @return the dVC_HOST_NAME
     */
    public String getDVC_HOST_NAME() {
        return DVC_HOST_NAME;
    }
    /**
     * @param dVC_HOST_NAME the dVC_HOST_NAME to set
     */
    public void setDVC_HOST_NAME(String dVC_HOST_NAME) {
        DVC_HOST_NAME = dVC_HOST_NAME;
    }
    /**
     * @return the dVC_PROCESS_NAME
     */
    public String getDVC_PROCESS_NAME() {
        return DVC_PROCESS_NAME;
    }
    /**
     * @param dVC_PROCESS_NAME the dVC_PROCESS_NAME to set
     */
    public void setDVC_PROCESS_NAME(String dVC_PROCESS_NAME) {
        DVC_PROCESS_NAME = dVC_PROCESS_NAME;
    }
    /**
     * @return the rEQUEST_URL
     */
    public String getREQUEST_URL() {
        return REQUEST_URL;
    }
    /**
     * @param rEQUEST_URL the rEQUEST_URL to set
     */
    public void setREQUEST_URL(String rEQUEST_URL) {
        REQUEST_URL = rEQUEST_URL;
    }
    /**
     * @return the eXTERNAL_ID
     */
    public String getEXTERNAL_ID() {
        return EXTERNAL_ID;
    }
    /**
     * @param eXTERNAL_ID the eXTERNAL_ID to set
     */
    public void setEXTERNAL_ID(String eXTERNAL_ID) {
        EXTERNAL_ID = eXTERNAL_ID;
    }
    /**
     * @return the dVC_OUTBOUND_INTERFACE
     */
    public String getDVC_OUTBOUND_INTERFACE() {
        return DVC_OUTBOUND_INTERFACE;
    }
    /**
     * @param dVC_OUTBOUND_INTERFACE the dVC_OUTBOUND_INTERFACE to set
     */
    public void setDVC_OUTBOUND_INTERFACE(String dVC_OUTBOUND_INTERFACE) {
        DVC_OUTBOUND_INTERFACE = dVC_OUTBOUND_INTERFACE;
    }
    /**
     * @return the dVC_EVENT_CLASS_ID
     */
    public String getDVC_EVENT_CLASS_ID() {
        return DVC_EVENT_CLASS_ID;
    }
    /**
     * @param dVC_EVENT_CLASS_ID the dVC_EVENT_CLASS_ID to set
     */
    public void setDVC_EVENT_CLASS_ID(String dVC_EVENT_CLASS_ID) {
        DVC_EVENT_CLASS_ID = dVC_EVENT_CLASS_ID;
    }
    /**
     * @return the cATEGORY_SIGNIFICANCE
     */
    public String getCATEGORY_SIGNIFICANCE() {
        return CATEGORY_SIGNIFICANCE;
    }
    /**
     * @param cATEGORY_SIGNIFICANCE the cATEGORY_SIGNIFICANCE to set
     */
    public void setCATEGORY_SIGNIFICANCE(String cATEGORY_SIGNIFICANCE) {
        CATEGORY_SIGNIFICANCE = cATEGORY_SIGNIFICANCE;
    }
    /**
     * @return the cATEGORY_BEHAVIOR
     */
    public String getCATEGORY_BEHAVIOR() {
        return CATEGORY_BEHAVIOR;
    }
    /**
     * @param cATEGORY_BEHAVIOR the cATEGORY_BEHAVIOR to set
     */
    public void setCATEGORY_BEHAVIOR(String cATEGORY_BEHAVIOR) {
        CATEGORY_BEHAVIOR = cATEGORY_BEHAVIOR;
    }
    /**
     * @return the cATEGORY_DEVICE_GROUP
     */
    public String getCATEGORY_DEVICE_GROUP() {
        return CATEGORY_DEVICE_GROUP;
    }
    /**
     * @param cATEGORY_DEVICE_GROUP the cATEGORY_DEVICE_GROUP to set
     */
    public void setCATEGORY_DEVICE_GROUP(String cATEGORY_DEVICE_GROUP) {
        CATEGORY_DEVICE_GROUP = cATEGORY_DEVICE_GROUP;
    }
    /**
     * @return the cATEGORY_OUTCOME
     */
    public String getCATEGORY_OUTCOME() {
        return CATEGORY_OUTCOME;
    }
    /**
     * @param cATEGORY_OUTCOME the cATEGORY_OUTCOME to set
     */
    public void setCATEGORY_OUTCOME(String cATEGORY_OUTCOME) {
        CATEGORY_OUTCOME = cATEGORY_OUTCOME;
    }
    /**
     * @return the cATEGORY_OBJECT
     */
    public String getCATEGORY_OBJECT() {
        return CATEGORY_OBJECT;
    }
    /**
     * @param cATEGORY_OBJECT the cATEGORY_OBJECT to set
     */
    public void setCATEGORY_OBJECT(String cATEGORY_OBJECT) {
        CATEGORY_OBJECT = cATEGORY_OBJECT;
    }
    /* (non-Javadoc)
     * @see entity.Event#setEnd_time(java.util.Date)
     */
    public void setEnd_time(Date date) {
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
