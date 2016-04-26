package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.ArcEvent;
import entity.ArcEventCorrelation;
import entity.Event;
import entity.ExceptionLog;

public class DBConnection {

    private Logger log = LoggerFactory.getLogger(getClass());
    private SessionFactory sessionFactory = null;

    private Session session = null;

    public void connect() throws FileNotFoundException, IOException {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream("./db.properties"));

            Configuration configuration = new Configuration();

            configuration.configure("hibernate.cfg.xml").addProperties(properties);
            configuration.addAnnotatedClass(ArcEvent.class);
            configuration.addAnnotatedClass(ArcEventCorrelation.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        if (session == null || !session.isOpen()) {

            try {
                session = sessionFactory.openSession();
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    public void close() {

        if (session != null) {
            session.close();
        }

        if (session != null) {
            sessionFactory.close();
        }
    }

    public void save(Event evt) {
        
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();
            session.save(evt);
            tx.commit();
        } catch (Exception e) {
            log.error(e.toString());
            tx.rollback();
        }
    }
    
    public List<ExceptionLog> getExceptionLog() {
        List<ExceptionLog> list = new ArrayList<ExceptionLog>();
        
        
        
        return list;
    }
    
    public void saveExceptionLog(String ceflog) throws IOException {
    
    }
    
    public void updateExceptionLog(long id) {
        
    }

    public static void main(String[] args) throws HibernateException {

        ArcEvent evt = new ArcEvent();
        evt.setESM_HOST("a");
        evt.setEVENT_ID(1111111);

        DBConnection db = new DBConnection();
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