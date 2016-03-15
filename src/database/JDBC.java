package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Java Database Connectivity 
 * Singleton class to handle the connection to the database
 * hosted on the comp421.cs.mcgill.ca server
 * 
 * Remember to openConnection() before performing any communications
 * and to closeConnections() when you are finished!
 * 
 * @author yarden
 */

public class JDBC{
    
    private static JDBC instance = null;
    
    
    private Statement statement;
    private Connection connection;
    
    private final String DB_USER_NAME = "cs421g01";
    private final String DB_PASSWORD = "snowio01";
    private final String DB_URL = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
    
    // Singleton class
    private JDBC(){
        registerDriver();
    }
    
    public static JDBC getInstance(){
        if(instance == null){
            instance = new JDBC();
        }
        return instance;
    }
    
    public void openConnection(){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void registerDriver(){
        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
        } catch (Exception cnfe){
            System.out.println("Class not found");
        }    
    }
    
    public Statement getStatement(){
        return this.statement;
    }
}
