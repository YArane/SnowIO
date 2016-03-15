package database;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;

import entities.Supplier;

public class SupplierDB {
   static final String JDBC_DRIVER =
      "com.mysql.jdbc.Driver";
   static final String DB_URL =
      "jdbc:mysql://localhost:3306/mydatabase";
   static final String DB_USER = "user1";
   static final String DB_PASS = "secret";
   private JdbcRowSet rowSet = null;
   public SupplierDB() {
      try {
         Class.forName(JDBC_DRIVER);
         rowSet = new JdbcRowSetImpl();
         rowSet.setUrl(DB_URL);
         rowSet.setUsername(DB_USER);
         rowSet.setPassword(DB_PASS);
         rowSet.setCommand("SELECT * FROM Person");
         rowSet.execute();
      }catch (SQLException | ClassNotFoundException e) {
         e.printStackTrace();
      }
   }
   public Supplier create(Supplier s) {
      try {
         rowSet.moveToInsertRow();
         rowSet.updateString("supplierID", s.getSupplierID());
         rowSet.updateString("firstName", s.getAddress());
         rowSet.updateString("middleName", s.getName());
         rowSet.insertRow();
         rowSet.moveToCurrentRow();
      } catch (SQLException ex) {
         try {
            rowSet.rollback();
            s = null;
         } catch (SQLException e) {

         }
         ex.printStackTrace();
      }
      return s;
   }

   public Supplier update(Supplier s) {
      try {
         rowSet.updateString("supplierID", s.getSupplierID());
         rowSet.updateString("firstName", s.getAddress());
         rowSet.updateString("address", s.getName());
         rowSet.updateRow();
         rowSet.moveToCurrentRow();
      } catch (SQLException ex) {
         try {
            rowSet.rollback();
         } catch (SQLException e) {

         }
         ex.printStackTrace();
      }
      return s;
   }

   public void delete() {
      try {
         rowSet.moveToCurrentRow();
         rowSet.deleteRow();
      } catch (SQLException ex) {
         try {
            rowSet.rollback();
         } catch (SQLException e) { }
         ex.printStackTrace();
      }

   }
}