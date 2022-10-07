package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DRIVER;

public class Util {
    private static Connection bdConnection;
    private static final String bdHost = "localhost";
    private static final String bdPort = "3306";
    private static final String bdName = "mybd";
    private static final String bdUser = "root";
    private static final String bdPass = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String bdURL = "jdbc:mysql://" + bdHost + ":" + bdPort + "/" + bdName;

    private static SessionFactory sessionFactory;


    public static Connection getBdConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            bdConnection = DriverManager.getConnection(bdURL, bdUser, bdPass);
            System.out.println("2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            bdConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bdConnection;
    }

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mybd");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "false");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                //settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
