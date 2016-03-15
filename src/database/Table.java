package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Table class to represent a SQL relation
 * 
 * @author yarden
 *
 */

public class Table {
    
    protected String tableName;
    protected Statement statement;
    
    private JDBC jdbc;
    
    private int sqlCode=0;      // Variable to hold SQLCODE
    private String sqlState="00000";  // Variable to hold SQLSTATE
    
    public Table(String tableName){
        this.tableName = tableName;
        this.jdbc = JDBC.getInstance();
        this.statement = jdbc.getStatement();
    }
    
    // Example input:
    //String createSQL = "CREATE TABLE " + tableName + "(id INTEGER, name VARCHAR (30), address VARCHAR(30))";
    public void createTable(String createSQL){
        try{
            statement.executeUpdate(createSQL);
        }catch(SQLException e){
            handleSQLException(e);
        }
    }
    
    // Example input:
    //String insertSQL = "INSERT INTO " + tableName + " VALUES ( 1 , \'Vicki\' ) ";
    public void insert(String insertSQL){
        // Inserting Data into the table
        try {
            statement.executeUpdate (insertSQL);
        }catch (SQLException e){
            handleSQLException(e);
        }
    }
    
    // Querying a table-
    public ResultSet query(String querySQL){
        try {
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            return rs;
        }catch (SQLException e){
            handleSQLException(e);
        }
        return null;
    }
    
    //Updating a table-
    public void update(String updateSQL){
        try{
            statement.executeUpdate(updateSQL);
        }catch (SQLException e){
            handleSQLException(e);
        }
    }
    
    private void handleSQLException(SQLException e){
        sqlCode = e.getErrorCode(); // Get SQLCODE
        sqlState = e.getSQLState(); // Get SQLSTATE
        // Your code to handle errors comes here;-
        // something more meaningful than a print would be good-
        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
    }
}
