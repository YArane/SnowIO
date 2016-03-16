package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
    
    private boolean connected;
    
    private Statement statement;
    private Connection connection;
    
    private final String DB_USER_NAME = "cs421g01";
    private final String DB_PASSWORD = "snowio01";
    private final String DB_URL = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
    
    private int sqlCode=0;      // Variable to hold SQLCODE
    private String sqlState="00000";  // Variable to hold SQLSTATE
    public static final String SUCCESS = "Success";
    
    
    // Singleton class
    private JDBC(){
        this.connected = false;
        registerDriver();
    }
    
    public static JDBC getInstance(){
        if(instance == null){
            instance = new JDBC();
        }
        return instance;
    }
    
    /**
     * Opens a connection to the database
     * 
     * @param DB_PASSWORD
     * @return error code
     */
    public String openConnection(String DB_PASSWORD){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
            statement = connection.createStatement();
            this.connected = true;

            System.out.println("Successfully connected to " + DB_URL);
            System.out.println(statement);
            return SUCCESS;
        } catch (SQLException e) {
            return handleSQLException(e);
        }
    }
    
    /**
     * Updates the database with the input updateSQL
     * 
     * @param updateSQL
     */
    public String update(String updateSQL){
        try{
            statement.executeUpdate(updateSQL);
            return SUCCESS;
        }catch (SQLException e){
            return handleSQLException(e);
        }
    }
    
    /**
     * Queries the database with input querySQL
     * 
     * @param querySQLm
     * @return
     */
    public ResultSet query(String querySQL){
        try {
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            return rs;
        }catch (SQLException e){
            handleSQLException(e);
        }
        return null;
    }
    
    /**
     * Closes the connection to the database
     * @return error code
     */
    public String closeConnection(){
        if(this.connected)
            try {
                statement.close();
                connection.close();
                this.connected = false;
                return SUCCESS;
            } catch (SQLException e) {
                return handleSQLException(e);
            }
        return "No connection established.";
    }
    
    private void registerDriver(){
        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver(new org.postgresql.Driver()) ;
            System.out.println("Driver Registered.");
        } catch (Exception cnfe){
            System.out.println("Class not found");
        }    
    }
    
    public Statement getStatement(){
        return this.statement;
    }
    
    private String handleSQLException(SQLException e){
        sqlCode = e.getErrorCode(); // Get SQLCODE
        sqlState = e.getSQLState(); // Get SQLSTATE
        // Your code to handle errors comes here;-
        // something more meaningful than a print would be good
        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        return ("Code: " + sqlCode + "  sqlState: " + sqlState);
    }
    
    public boolean isConnected(){
        return this.connected;
    }
}
