package controller;

import java.sql.ResultSet;

import database.JDBC;

public class Queries {
   
   /**
    * Class to hold Postgresql operations 
    */
    
    public static ResultSet getCurrentRentals(){
        JDBC jdbc = JDBC.getInstance();
        String currentRentalsQuery = "SELECT (name, phone_number, date_in, date_out) FROM "
                                   + "rentalorders INNER JOIN customers "
                                   + "ON rentalorders.customer_id = customers.customer_id;";
        ResultSet rs = jdbc.query(currentRentalsQuery);
        System.out.println(rs);
        return rs;
    }

}
