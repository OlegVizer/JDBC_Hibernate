package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
   private static Connection bdConnection;
   private static final String bdHost = "localhost";
   private static final String bdPort = "3306";
   private static final String bdName = "mybd";
   private static final String bdUser = "root";
   private static final String bdPass = "root";

    public static Connection getBdConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String bdURL ="jdbc:mysql://" + bdHost + ":" + bdPort + "/" + bdName;
        try {
            bdConnection = DriverManager.getConnection(bdURL, bdUser, bdPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bdConnection;
    }
}
