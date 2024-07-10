package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection conn;
    private static final String url = "jdbc:mysql://localhost:3306/revtaskmanagement";
    private static final String user = "root";
    private static final String password = "root";
    public static DBConnection db;

    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database is not connected : "+e.getMessage());
        }
    }

    public Connection getDBConnection(){
        return conn;
    }


    public static DBConnection getInstance(){
        if(db==null){
            db=new DBConnection();
        }
        return db;
    }
}
