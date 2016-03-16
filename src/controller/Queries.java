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
    
    /**
     * 
     * @param options RentalOrderOptions to handle default values and/or options for the query
     * @return ResultSet result of query
     */
    public static ResultSet getRentalOrders(RentalOrderOptions options) {
    	JDBC jdbc = JDBC.getInstance();
    	String rentalOrderQuery = "SELECT (RO.Rental_Order_ID, RO.Date_In, RO.Date_Out, E.Name AS Employee_Name, C.Name AS Customer_Name, RO.Total_Price) "
    							+ "FROM RentalOrders RO"
    							+ "INNER JOIN Employees E ON E.Employee_ID = RO.Employee_ID "
    							+ "INNER JOIN Customers C ON C.Customer_ID = RO.Customer_ID ";
    	
    	// OPTIONS
    	if(!options.getCustomerName().equals("")) {
    		rentalOrderQuery += "WHERE Customer_Name LIKE '%" + options.getCustomerName() + "%' ";
    	}
    	if(!options.getCustomerName().equals("")) {
    		rentalOrderQuery += "WHERE Employee_Name LIKE '%" + options.getEmployeeName() + "%' ";
    	}
    	
    	
    	// ORDERING
    	switch(options.getOrdering()) {
    	case CUSTOMER:
    		rentalOrderQuery += "ORDER BY Customer_Name";
    	case EMPLOYEE:
    		rentalOrderQuery += "ORDER BY Employee_Name";
    	case TOTAL_PRICE:
    		rentalOrderQuery += "ORDER BY RO.TotalPrice";
    	case DATE_IN:
    		rentalOrderQuery += "ORDER BY RO.Date_In";
    	case DATE_OUT:
    		rentalOrderQuery += "ORDER BY RO.Date_Out";
    	}
    	
    	rentalOrderQuery += ";";
    	
    	ResultSet rs = jdbc.query(rentalOrderQuery);
        System.out.println(rs);
        return rs;
    }
    
    public static ResultSet getRentalOrderByID(String rentalOrderID) {
    	JDBC jdbc = JDBC.getInstance();
    	String rentalOrderQuery = "SELECT (RO.Date_In, RO.Date_Out, E.Name AS Employee_Name, E.Type AS Employee_Type, C.Name AS Customer_Name, C.Address AS Customer_Address, C.Phone_Number AS Customer_Phone_Number) "
    							+ "FROM RentalOrders RO"
    							+ "INNER JOIN Employees E ON E.Employee_ID = RO.Employee_ID "
    							+ "INNER JOIN Customers C ON C.Customer_ID = RO.Customer_ID "
    							+ "WHERE RO.Rental_Order_ID = '" + rentalOrderID + "';";
    	ResultSet rs = jdbc.query(rentalOrderQuery);
        System.out.println(rs);
        return rs;
    }
}
