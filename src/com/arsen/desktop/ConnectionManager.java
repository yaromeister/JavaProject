package com.arsen.desktop;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    // JDBC driver name and database URL
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/java_project";
    private static final String DB_URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net/heroku_7bf74b485ff3cdb?reconnect=true";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //  Database credentials
    //private static final String USER = "root";
    //private static final String PASS = "admin";
    private static final String PASS = "a8546289";
    private static final String USER = "b14b99260eee75";

    private static Connection con = null;

    public static Connection getConnection(){
        if (con == null){
            try {
                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                return conn;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Unable to connect, please check your connection and try again");
                return null;
            }
        }else {
            return con;
        }


    }
}
