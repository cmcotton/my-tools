package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.ArcEvent;
import entity.ArcEventCorrelation;
import entity.Event;
import entity.ExceptionLog;

public class DBConnectionNative extends DBConnection {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Connection con = null; // Database objects

    public void connect() throws FileNotFoundException, IOException {
        
        try {
            if (con == null || con.isClosed()) {
            
                FileInputStream inputStream = new FileInputStream("./db.properties");
                Properties prop = new Properties();
                prop.load(inputStream);
   
                String user = "";
                String adv = "";
                String url = "";
                user = prop.getProperty("hibernate.connection.username");
                adv = prop.getProperty("hibernate.connection.password");
                url = prop.getProperty("hibernate.connection.url");
   
                try {
                    Class.forName("com.mysql.jdbc.Driver");
   
                    // 註冊driver
                    con = DriverManager.getConnection(url, user, adv);
                    // 取得connection
   
                    // jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
                    // localhost是主機名,test是database名
                    // useUnicode=true&characterEncoding=Big5使用的編碼
   
                } catch (ClassNotFoundException e) {
                    logger.error("DriverClassNotFound :" + e.toString());
                }// 有可能會產生sqlexception
                catch (SQLException x) {
                    logger.error("Exception :" + x.toString());
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close() {

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void save(Event evt) throws FileNotFoundException, IOException {
        PreparedStatement stmt = null;

        String sql;

        if (evt instanceof ArcEvent) {
            sql = "INSERT INTO arc_event ("
                    + "ESM_HOST, EVENT_ID, START_TIME, NAME, PRIORITY, "
                    + // 5
                    "SRC_ADDRESS, SRC_PORT, SOURCE_COUNTRY_NAME, DEST_ADDRESS, "
                    + // 4
                    "DEST_PORT, DESC_COUNTRY_NAME, DEST_HOST_NAME, BASE_EVENT_COUNT, "
                    + // 4
                    "MANAGER_RECEIPT_TIME, DVC_ACTION, DVC_CUSTOM_STRING1, FILE_NAME, "
                    + // 4
                    "DVC_PRODUCT, EVENT_TYPE, DVC_CUSTOM_IPV6_ADDRESS1, DVC_CUSTOM_IPV6_ADDRESS2, "
                    + // 4
                    "DVC_CUSTOM_IPV6_ADDRESS3, DVC_CUSTOM_IPV6_ADDRESS4, DVC_CUSTOM_STRING2, DVC_CUSTOM_STRING3, " // 4
                    + "DVC_CUSTOM_STRING4, DVC_CUSTOM_STRING5, DVC_CUSTOM_STRING6, SRC_HOST_NAME, "// 4
                    + "FILE_PATH, DVC_VENDOR, SRC_USER_NAME, DEST_USER_NAME, DVC_ADDRESS,"// 5
                    + "DVC_HOST_NAME, DVC_PROCESS_NAME, REQUEST_URL, EXTERNAL_ID,"// 4
                    + "DVC_OUTBOUND_INTERFACE, DVC_EVENT_CLASS_ID, CATEGORY_SIGNIFICANCE, CATEGORY_BEHAVIOR,"// 4
                    + "CATEGORY_DEVICE_GROUP, CATEGORY_OUTCOME, CATEGORY_OBJECT) "
                    + // 3
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?) ";

            try {
                stmt = con.prepareStatement(sql);

                int index = 1;

                Date startTime = ((ArcEvent) evt).getSTART_TIME();
                Date mgr = ((ArcEvent) evt).getMANAGER_RECEIPT_TIME();

                stmt.setString(index++, ((ArcEvent) evt).getESM_HOST());
                stmt.setLong(index++, ((ArcEvent) evt).getEVENT_ID());
                stmt.setTimestamp(index++, new java.sql.Timestamp(startTime.getTime()));
                stmt.setString(index++, ((ArcEvent) evt).getNAME());
                stmt.setInt(index++, ((ArcEvent) evt).getPRIORITY());
                stmt.setLong(index++, ((ArcEvent) evt).getSRC_ADDRESS());
                stmt.setInt(index++, ((ArcEvent) evt).getSRC_PORT());
                stmt.setString(index++, ((ArcEvent) evt).getSOURCE_COUNTRY_NAME());
                stmt.setLong(index++, ((ArcEvent) evt).getDEST_ADDRESS());
                stmt.setInt(index++, ((ArcEvent) evt).getDEST_PORT());
                stmt.setString(index++, ((ArcEvent) evt).getDESC_COUNTRY_NAME());
                stmt.setString(index++, ((ArcEvent) evt).getDEST_HOST_NAME());
                stmt.setInt(index++, ((ArcEvent) evt).getBASE_EVENT_COUNT());
                stmt.setTimestamp(index++, new java.sql.Timestamp(mgr.getTime()));
                stmt.setString(index++, ((ArcEvent) evt).getDVC_ACTION());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_STRING1());
                stmt.setString(index++, ((ArcEvent) evt).getFILE_NAME());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_PRODUCT());
                stmt.setString(index++, ((ArcEvent) evt).getEVENT_TYPE());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_IPV6_ADDRESS1());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_IPV6_ADDRESS2());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_IPV6_ADDRESS3());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_IPV6_ADDRESS4());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_STRING2());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_STRING3());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_STRING4());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_STRING5());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_CUSTOM_STRING6());
                stmt.setString(index++, ((ArcEvent) evt).getSRC_HOST_NAME());
                stmt.setString(index++, ((ArcEvent) evt).getFILE_PATH());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_VENDOR());
                stmt.setString(index++, ((ArcEvent) evt).getSRC_USER_NAME());
                stmt.setString(index++, ((ArcEvent) evt).getDEST_USER_NAME());
                stmt.setLong(index++, ((ArcEvent) evt).getDVC_ADDRESS());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_HOST_NAME());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_PROCESS_NAME());
                stmt.setString(index++, ((ArcEvent) evt).getREQUEST_URL());
                stmt.setString(index++, ((ArcEvent) evt).getEXTERNAL_ID());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_OUTBOUND_INTERFACE());
                stmt.setString(index++, ((ArcEvent) evt).getDVC_EVENT_CLASS_ID());
                stmt.setString(index++, ((ArcEvent) evt).getCATEGORY_SIGNIFICANCE());
                stmt.setString(index++, ((ArcEvent) evt).getCATEGORY_BEHAVIOR());
                stmt.setString(index++, ((ArcEvent) evt).getCATEGORY_DEVICE_GROUP());
                stmt.setString(index++, ((ArcEvent) evt).getCATEGORY_OUTCOME());
                stmt.setString(index++, ((ArcEvent) evt).getCATEGORY_OBJECT());
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        } else if (evt instanceof ArcEventCorrelation) {
            sql = "INSERT INTO arc_event_correlation ("
                    + "BASE_EVENT_IDS, ESM_HOST, CORRELATED_EVENT_ID, end_time) " + // 3
                    "VALUES (?, ?, ?, ?) ";

            try {
                stmt = con.prepareStatement(sql);

                int index = 1;

                Date endTime = ((ArcEventCorrelation) evt).getEnd_time();

                stmt.setString(index++, ((ArcEventCorrelation) evt).getBASE_EVENT_IDS());
                stmt.setString(index++, ((ArcEventCorrelation) evt).getESM_HOST());
                stmt.setLong(index++, ((ArcEventCorrelation) evt).getCORRELATED_EVENT_ID());
                stmt.setTimestamp(index++, new java.sql.Timestamp(endTime.getTime()));
                
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        }

        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {

        }
    }
    
    public void saveExceptionLog(String ceflog) throws FileNotFoundException, IOException {
        PreparedStatement stmt = null;

        String sql = "INSERT INTO exception_log (log, exception_time) " + 
                "VALUES (?, ?) ";

        try {
            stmt = con.prepareStatement(sql);

            int index = 1;
            
            stmt.setString(index++, ceflog);
            stmt.setTimestamp(index++, new java.sql.Timestamp(new Date().getTime()));
                            
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    

        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {

        }
    }
    
    public List<ExceptionLog> getExceptionLog() {
        List<ExceptionLog> list = new ArrayList<ExceptionLog>();
        
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM exception_log WHERE retry_time = 0";

        try {
            stmt = con.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    
        try {
            ResultSet rs = stmt.executeQuery();
            
            
            int index = 1;
            while (rs.next()) {
                ExceptionLog el = new ExceptionLog();
                
                el.setId(rs.getLong(index++));
                el.setLog(rs.getString(index++));
                el.setExceptionTime(rs.getDate(index++));
                el.setRetryTime(rs.getInt(index++));
                
                list.add(el);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        return list;
    }

    public void updateExceptionLog(long id) {
        PreparedStatement stmt = null;

        String sql = "SELECT retry_time FROM exception_log WHERE id = ?";
        
        int retryTime = 0;
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                retryTime = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        sql = "UPDATE exception_log SET retry_time = ?, exception_time = ? WHERE id = ?";
        try {
            int index = 1;
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(index++, retryTime + 1);
            stmt.setTimestamp(index++, new java.sql.Timestamp(new Date().getTime()));
            stmt.setLong(index++, id);
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public static void main(String[] args) throws HibernateException {

        ArcEvent evt = new ArcEvent();
        evt.setESM_HOST("a");
        evt.setEVENT_ID(1111111);

        DBConnectionNative db = new DBConnectionNative();
        try {
            db.connect();
            db.save(evt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        db.close();

        System.out.println("新增資料OK!請先用MySQL觀看結果！");
    }
}