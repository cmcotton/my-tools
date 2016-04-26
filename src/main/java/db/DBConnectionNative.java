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

    public void save(Event evt) {
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
                    + "CATEGORY_DEVICE_GROUP, CATEGORY_OUTCOME, CATEGORY_OBJECT, DEVICE_EVENT_CATEGORY) "
                    + // 3
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?) ";

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
                stmt.setString(index++, ((ArcEvent) evt).getDEVICE_EVENT_CATEGORY());
            } catch (Exception e) {
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
                
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }

        try {
            stmt.execute();
        } catch (Exception e) {
            logger.error(e.toString());
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
    
    public List<Event> exportArcEventOldData(int day) {
        List<Event> results = new ArrayList<>();
        
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM arc_event WHERE start_time < DATE_ADD(NOW(),INTERVAL ? DAY)";
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, -day);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ArcEvent row = new ArcEvent();
                
                row.setId(rs.getLong("id"));
                row.setESM_HOST(rs.getString("ESM_HOST"));
                row.setEVENT_ID(rs.getLong("EVENT_ID"));
                row.setSTART_TIME(rs.getDate("START_TIME"));
                row.setNAME(rs.getString("NAME"));
                row.setPRIORITY(rs.getInt("PRIORITY"));
                row.setSRC_ADDRESS(rs.getLong("SRC_ADDRESS"));
                row.setSRC_PORT(rs.getInt("SRC_PORT"));                
                row.setSOURCE_COUNTRY_NAME(rs.getString("SOURCE_COUNTRY_NAME"));
                row.setDEST_ADDRESS(rs.getLong("DEST_ADDRESS"));
                row.setDEST_PORT(rs.getInt("DEST_PORT"));
                row.setDESC_COUNTRY_NAME(rs.getString("DESC_COUNTRY_NAME"));
                row.setDEST_HOST_NAME(rs.getString("DEST_HOST_NAME"));
                row.setBASE_EVENT_COUNT(rs.getInt("BASE_EVENT_COUNT"));
                row.setMANAGER_RECEIPT_TIME(rs.getDate("MANAGER_RECEIPT_TIME"));
                row.setDVC_ACTION(rs.getString("DVC_ACTION"));
                row.setDVC_CUSTOM_STRING1(rs.getString("DVC_CUSTOM_STRING1"));
                row.setFILE_NAME(rs.getString("FILE_NAME"));
                row.setDVC_PRODUCT(rs.getString("DVC_PRODUCT"));
                row.setEVENT_TYPE(rs.getString("EVENT_TYPE"));
                row.setDVC_CUSTOM_IPV6_ADDRESS1(rs.getString("DVC_CUSTOM_IPV6_ADDRESS1"));
                row.setDVC_CUSTOM_IPV6_ADDRESS2(rs.getString("DVC_CUSTOM_IPV6_ADDRESS2"));
                row.setDVC_CUSTOM_IPV6_ADDRESS3(rs.getString("DVC_CUSTOM_IPV6_ADDRESS3"));
                row.setDVC_CUSTOM_IPV6_ADDRESS4(rs.getString("DVC_CUSTOM_IPV6_ADDRESS4"));
                row.setDVC_CUSTOM_STRING2(rs.getString("DVC_CUSTOM_STRING2"));
                row.setDVC_CUSTOM_STRING3(rs.getString("DVC_CUSTOM_STRING3"));
                row.setDVC_CUSTOM_STRING4(rs.getString("DVC_CUSTOM_STRING4"));
                row.setDVC_CUSTOM_STRING5(rs.getString("DVC_CUSTOM_STRING5"));
                row.setDVC_CUSTOM_STRING6(rs.getString("DVC_CUSTOM_STRING6"));
                row.setSRC_HOST_NAME(rs.getString("SRC_HOST_NAME"));
                row.setFILE_PATH(rs.getString("FILE_PATH"));
                row.setDVC_VENDOR(rs.getString("DVC_VENDOR"));
                row.setSRC_USER_NAME(rs.getString("SRC_USER_NAME"));
                row.setDEST_USER_NAME(rs.getString("DEST_USER_NAME"));
                row.setDVC_ADDRESS(rs.getLong("DVC_ADDRESS"));
                row.setDVC_HOST_NAME(rs.getString("DVC_HOST_NAME"));
                row.setDVC_PROCESS_NAME(rs.getString("DVC_PROCESS_NAME"));
                row.setREQUEST_URL(rs.getString("REQUEST_URL"));
                row.setEXTERNAL_ID(rs.getString("EXTERNAL_ID"));
                row.setDVC_OUTBOUND_INTERFACE(rs.getString("DVC_OUTBOUND_INTERFACE"));
                row.setDVC_EVENT_CLASS_ID(rs.getString("DVC_EVENT_CLASS_ID"));
                row.setCATEGORY_SIGNIFICANCE(rs.getString("CATEGORY_SIGNIFICANCE"));
                row.setCATEGORY_BEHAVIOR(rs.getString("CATEGORY_BEHAVIOR"));
                row.setCATEGORY_DEVICE_GROUP(rs.getString("CATEGORY_DEVICE_GROUP"));
                row.setCATEGORY_OUTCOME(rs.getString("CATEGORY_OUTCOME"));
                row.setCATEGORY_OBJECT(rs.getString("CATEGORY_OBJECT"));
                
                results.add(row);
            }
                        
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        return results;
    }
    
    public List<Event> exportArcEventCorrelationOldData(int day) {
        List<Event> results = new ArrayList<>();
        
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM arc_event_correlation WHERE end_time < DATE_ADD(NOW(),INTERVAL ? DAY)";
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, -day);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ArcEventCorrelation row = new ArcEventCorrelation();
                
                row.setId(rs.getLong("id"));
                row.setBASE_EVENT_IDS(rs.getString("BASE_EVENT_IDS"));
                row.setCORRELATED_EVENT_ID(rs.getLong("CORRELATED_EVENT_ID"));
                row.setEnd_time(rs.getDate("end_time"));
                row.setESM_HOST(rs.getString("ESM_HOST"));                
                
                results.add(row);
            }
                        
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        return results;
    }
    
    public List<Event> exportExceptionLogOldData(int day) {
        List<Event> results = new ArrayList<>();
        
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM exception_log WHERE exception_time < DATE_ADD(NOW(),INTERVAL ? DAY)";
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, -day);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ExceptionLog row = new ExceptionLog();
                
                row.setId(rs.getLong("id"));
                row.setLog(rs.getString("log"));
                row.setExceptionTime(rs.getDate("exception_time"));
                row.setRetryTime(rs.getInt("retry_time"));                
                
                results.add(row);
            }
                        
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        return results;
    }
    
    public void deleteOldData(int day) {
        PreparedStatement stmt = null;

        String sql = "DELETE FROM arc_event WHERE start_time < DATE_ADD(NOW(),INTERVAL ? DAY)";        
        
        try {
            int idx = 1;
            stmt = con.prepareStatement(sql);
            stmt.setInt(idx++, -day);            
            stmt.execute();
                        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        
        sql = "DELETE FROM arc_event_correlation WHERE end_time < DATE_ADD(NOW(),INTERVAL ? DAY)";
        try {
            int idx = 1;
            stmt = con.prepareStatement(sql);
            stmt.setInt(idx++, -day);            
            stmt.execute();
                        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        
        sql ="DELETE FROM exception_log WHERE exception_time < DATE_ADD(NOW(),INTERVAL ? DAY)";
        try {
            int idx = 1;
            stmt = con.prepareStatement(sql);
            stmt.setInt(idx++, -day);            
            stmt.execute();
                        
        } catch (Exception e) {
            e.printStackTrace();
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