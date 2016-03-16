package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.JDBC;

public class Queries {

    /**
     * 
     * @param options RentalOrderOptions to handle default values and/or options for the query
     * @return ResultSet result of query
     */
    public static ResultSet getRentalOrders(RentalOrderOptions options) {
    	JDBC jdbc = JDBC.getInstance();
    	String rentalOrderQuery = "SELECT RO.Rental_Order_ID, RO.Date_In, RO.Date_Out, E.Name AS Employee_Name, C.Name AS Customer_Name, RO.Total_Price "
    							+ "FROM RentalOrders RO "
    							+ "INNER JOIN Employees E ON E.Employee_ID = RO.Employee_ID "
    							+ "INNER JOIN Customers C ON C.Customer_ID = RO.Customer_ID ";
    	
    	// OPTIONS
    	String whereClause = "";
    	if(!options.getCustomerName().equals("")) {
    		whereClause += "C.Name LIKE '%" + options.getCustomerName() + "%' ";
    	}
    	if(!options.getEmployeeName().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "E.Name LIKE '%" + options.getEmployeeName() + "%' ";
    	}
    	if(options.getCurrentOrders() && !options.getPastOrders()) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "(RO.Date_In IS NULL OR RO.Date_In > now()) ";
    	} else if (options.getPastOrders() && !options.getCurrentOrders()) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "RO.Date_In IS NOT NULL AND RO.Date_In < now() ";
    	}
    	if(!whereClause.equals("")) {
			whereClause = "WHERE " + whereClause;
		}    	
    	rentalOrderQuery += whereClause;
    	
    	// ORDERING
    	switch(options.getOrdering()) {
    	case CUSTOMER:
    		rentalOrderQuery += "ORDER BY Customer_Name";
    		break;
    	case EMPLOYEE:
    		rentalOrderQuery += "ORDER BY Employee_Name";
    		break;
    	case TOTAL_PRICE:
    		rentalOrderQuery += "ORDER BY RO.Total_Price";
    		break;
    	case DATE_IN:
    		rentalOrderQuery += "ORDER BY RO.Date_In";
    		break;
    	case DATE_OUT:
    		rentalOrderQuery += "ORDER BY RO.Date_Out";
    		break;
    	}
    	
    	rentalOrderQuery += ";";
        System.out.println(rentalOrderQuery);
    	ResultSet rs = jdbc.query(rentalOrderQuery);
    	
        return rs;
    }
    
    public static ResultSet getRentalOrderByID(String rentalOrderID) {
    	JDBC jdbc = JDBC.getInstance();
    	String rentalOrderQuery = "SELECT RO.Date_In, RO.Date_Out, E.Name AS Employee_Name, E.Type AS Employee_Type, C.Name AS Customer_Name, C.Address AS Customer_Address, C.Phone_Number AS Customer_Phone_Number "
    							+ "FROM RentalOrders RO"
    							+ "INNER JOIN Employees E ON E.Employee_ID = RO.Employee_ID "
    							+ "INNER JOIN Customers C ON C.Customer_ID = RO.Customer_ID "
    							+ "WHERE RO.Rental_Order_ID = '" + rentalOrderID + "';";
    	ResultSet rs = jdbc.query(rentalOrderQuery);
       
        return rs;
    }
}
