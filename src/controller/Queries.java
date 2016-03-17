package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

//import controller.CustomerOptions.RentalStatus;

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

	public static String insertNewCustomer(CustomerOptions newCustomer) {
		String billingInserResult = insertNewBillingInfoData(newCustomer);
		System.out.println("Billing Insert Res:" + billingInserResult);

		JDBC jdbc = JDBC.getInstance();

		String insertCustomerQuery = "INSERT INTO Customers (name, address, credit_card_number, phone_number, age, weight_kg, height_cm) "
								   + "VALUES ('" + newCustomer.getCustomerName() + "', '" + newCustomer.getCustomerAddress()
								   + "', '" + newCustomer.getCreditCardNumber() + "', '" + newCustomer.getCustomerPhone()
				    			   + "', '" + newCustomer.getCustomerAge() + "', '" + newCustomer.getWeight()
								   + "', '" + newCustomer.getHeight() + "');";

		return jdbc.update(insertCustomerQuery);
	}

	private static String insertNewBillingInfoData(CustomerOptions newCustomer) {
		JDBC jdbc = JDBC.getInstance();
		String insertBillingInfoQuery = "INSERT INTO BillingInformation "
									  + "VALUES ('" + newCustomer.getCreditCardNumber() + "', '"
									  + newCustomer.getCardholderName() + "', '"
				 					  + newCustomer.getCreditCardType() + "', '"
									  + newCustomer.getBillingAddress() + "', '"
		   						      + newCustomer.getCVV() + "');";

		return jdbc.update(insertBillingInfoQuery);
	}

	public static ResultSet getCustomers() {
		JDBC jdbc = JDBC.getInstance();
		String getCustomersQuery = "SELECT * FROM Customers";
		return jdbc.query(getCustomersQuery);
	}

	public static ResultSet getCustomers(String searchName) {
		JDBC jdbc = JDBC.getInstance();
		String getCustomersQuery = "SELECT * FROM Customers WHERE Name Like '%" + searchName + "%';";
		return jdbc.query(getCustomersQuery);
	}

    /*public static ResultSet getCustomers(CustomerOptions options) {
    	JDBC jdbc = JDBC.getInstance();
    	String customersQuery = "SELECT C.Customer_ID, C.Name, C.Address, C.Phone_Number, HRO.Has_Rental_Order, count(RO.Rental_Order_ID) AS Total_Rentals, sum(RO.Total_Price) AS Total_Amount_Paid, C.Age, C.Weight_kg, C.Height_cm, C.Credit_Card_Number, BI.Type, BI.Billing_Address, BI.CVV "
    							+ "FROM Customers C "
    							+ "INNER JOIN BillingInformation BI ON BI.Credit_Card_Number = C.Credit_Card_Number "
    							+ "INNER JOIN RentalOrders RO ON C.Customer_ID = RO.Customer_ID "
    							+ "INNER JOIN ("
    							+ "(SELECT C.Customer_ID, 'Yes' AS Has_Rental_Order FROM RentalOrders RO LEFT JOIN Customers C ON C.Customer_ID = RO.Customer_ID AND C.Customer_ID IS NOT NULL GROUP BY C.Customer_ID)"
    							+ " UNION "
    							+ "(SELECT C.Customer_ID, 'No' AS Has_Rental_Order FROM RentalOrders RO LEFT JOIN Customers C ON C.Customer_ID = RO.Customer_ID AND C.Customer_ID IS NULL GROUP BY C.Customer_ID)"
    							+ ") HRO ON HRO.Customer_ID = C.Customer_ID ";
    	
       	// OPTIONS
    	String whereClause = "";
    	if(options.getRentalStatus() == RentalStatus.CURRENTLY_RENTING) {
    		whereClause += "HRO.Has_Rental_Order = 'Yes' ";
    	}else if(options.getRentalStatus() == RentalStatus.NOT_CURRENTLY_RENTING) {
    		whereClause += "HRO.Has_Rental_Order = 'No' ";
    	}
    	if(!options.getName().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "C.Name LIKE '%" + options.getName() + "%' ";
    	}
    	if(!whereClause.equals("")) {
			whereClause = "WHERE " + whereClause;
		}    	
    	customersQuery += whereClause;
    	
    	// GROUP BY
    	customersQuery += "GROUP BY C.Customer_ID, BI.Credit_Card_Number, HRO.Has_Rental_Order ";
    	
    	// ORDERING
		/*
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
    	
    	customersQuery += ";";
        System.out.println(customersQuery);
    	ResultSet rs = jdbc.query(customersQuery);
    	
        return rs;
    }*/
}
