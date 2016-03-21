package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.jar.Pack200;

import controller.CustomerOptions.RentalStatus;

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
    
    public static ResultSet getSkisInRentalOrder(String rentalOrderID){
        JDBC jdbc = JDBC.getInstance();
        String skisQuery = "Select * From skis JOIN (Select it.item_id FROM "
                + "itemrentals it INNER JOIN items "
                + "ON it.item_id = items.item_id "
                + "Where rental_order_id = " + rentalOrderID + ") S "
                + "ON skis.item_id = S.item_id";

        System.out.println(skisQuery);
        
        return jdbc.query(skisQuery);
    }
    
    public static ResultSet getBootsInRentalOrder(String rentalOrderID){
        JDBC jdbc = JDBC.getInstance();
        String bootsQuery = "Select * From boots JOIN (Select it.item_id FROM "
                + "itemrentals it INNER JOIN items "
                + "ON it.item_id = items.item_id "
                + "Where rental_order_id = " + rentalOrderID + ") B "
                + "ON boots.item_id = B.item_id";
        
        System.out.println(bootsQuery);
        
        return jdbc.query(bootsQuery);
    }
    
    public static ResultSet getPolesInRentalOrder(String rentalOrderID){
        JDBC jdbc = JDBC.getInstance();
        String polesQuery = "Select * From poles JOIN (Select it.item_id FROM "
                           + "itemrentals it INNER JOIN items "
                           + "ON it.item_id = items.item_id "
                           + "Where rental_order_id = " + rentalOrderID + ") P "
                           + "ON poles.item_id = P.item_id";

        System.out.println(polesQuery);
        
        return jdbc.query(polesQuery);
    }
    
    public static ResultSet getRentalOrderByID(String rentalOrderID) {
    	JDBC jdbc = JDBC.getInstance();
    	String rentalOrderQuery = "SELECT RO.Date_In, RO.Date_Out, E.Name AS Employee_Name, E.Type AS Employee_Type, C.Name AS Customer_Name, C.Address AS Customer_Address, C.Phone_Number AS Customer_Phone_Number "
    							+ "FROM RentalOrders RO "
    							+ "INNER JOIN Employees E ON E.Employee_ID = RO.Employee_ID "
    							+ "INNER JOIN Customers C ON C.Customer_ID = RO.Customer_ID "
    							+ "WHERE RO.Rental_Order_ID = '" + rentalOrderID + "';";
    	System.out.println(rentalOrderQuery);
    	ResultSet rs = jdbc.query(rentalOrderQuery);
       
        return rs;
    }
    
	public static String insertNewCustomer(CustomerOptions newCustomer, BillingInfoOptions newBillingInfo) {
		String billingInserResult = insertNewBillingInfoData(newBillingInfo);
		System.out.println("Billing Insert Res:" + billingInserResult);

		JDBC jdbc = JDBC.getInstance();

		String insertCustomerQuery = "INSERT INTO Customers (name, address, credit_card_number, phone_number, age, weight_kg, height_cm) "
								   + "VALUES ('" + newCustomer.getName() + "', '" + newCustomer.getAddress()
								   + "', '" + newCustomer.getCreditCardNumber() + "', '" + newCustomer.getPhone()
				    			   + "', '" + newCustomer.getAge() + "', '" + newCustomer.getWeight()
								   + "', '" + newCustomer.getHeight() + "');";
		return jdbc.update(insertCustomerQuery);
	}

	private static String insertNewBillingInfoData(BillingInfoOptions newBillingInfo) {
		JDBC jdbc = JDBC.getInstance();
		String insertBillingInfoQuery = "INSERT INTO BillingInformation "
									  + "VALUES ('" + newBillingInfo.getCreditCardNumber() + "', '"
									  + newBillingInfo.getName() + "', '"
				 					  + newBillingInfo.getType() + "', '"
									  + newBillingInfo.getAddress() + "', '"
		   						      + newBillingInfo.getCVV() + "');";
		return jdbc.update(insertBillingInfoQuery);
	}

	public static ResultSet getCustomers(String searchName) {
		JDBC jdbc = JDBC.getInstance();
		String getCustomersQuery = "SELECT * FROM Customers WHERE Name Like '%" + searchName + "%';";
		return jdbc.query(getCustomersQuery);
	}

	public static ResultSet getItemsForRent() {
		JDBC jdbc = JDBC.getInstance();
		String getCustomersQuery = "SELECT * FROM Items;";
		return jdbc.query(getCustomersQuery);
	}

	public static ResultSet getBootsForRent() {
		JDBC jdbc = JDBC.getInstance();
		String getBootsForRentQuery = "(SELECT i.Item_ID, i.Condition, m.Model_Name, b.length_cm, b.size, b.strap "
								    + "FROM items i "
								    + "INNER JOIN boots b "
								    + "ON i.item_id = b.item_id "
								    + "INNER JOIN models m "
								    + "ON i.model_id = m.model_id "
								    + "WHERE i.Out_Of_Service = 'f' "
								    + "AND NOT i.Condition = 'Poor') "
								    + "EXCEPT "
								    + "(SELECT i.Item_ID, i.Condition, m.Model_Name, b.length_cm, b.size, b.strap "
								    + "FROM itemrentals it "
								    + "INNER JOIN rentalorders re "
								    + "ON it.rental_order_id = re.rental_order_id "
								    + "INNER JOIN items i "
								    + "ON it.item_id = i.item_id "
								    + "INNER JOIN boots b "
								    + "ON it.item_id = b.item_id "
								    + "INNER JOIN models m "
								    + "ON i.model_id = m.model_id "
								    + "WHERE re.date_in IS NULL);";
		return jdbc.query(getBootsForRentQuery);
	}

	public static ResultSet getPolesForRent() {
		JDBC jdbc = JDBC.getInstance();
		String getPolesForRentQuery = "(SELECT i.Item_ID, i.Condition, m.Model_Name, s.length_cm, s.type "
								    + "FROM items i "
								    + "INNER JOIN poles s "
								    + "ON i.item_id = s.item_id "
								    + "INNER JOIN models m "
								    + "ON i.model_id = m.model_id "
								    + "WHERE i.Out_Of_Service = 'f' "
								    + "AND NOT i.Condition = 'Poor') "
								    + "EXCEPT "
								    + "(SELECT i.Item_ID, i.Condition, m.Model_Name, s.length_cm, s.type "
								    + "FROM itemrentals it "
								    + "INNER JOIN rentalorders re "
								    + "ON it.rental_order_id = re.rental_order_id "
								    + "INNER JOIN items i "
								    + "ON it.item_id = i.item_id "
								    + "INNER JOIN poles s "
								    + "ON it.item_id = s.item_id "
								    + "INNER JOIN models m "
								    + "ON i.model_id = m.model_id "
								    + "WHERE re.date_in IS NULL);";
		return jdbc.query(getPolesForRentQuery);
	}

	public static ResultSet getSkisForRent() {
		JDBC jdbc = JDBC.getInstance();
		String getSkisForRentQuery = "(SELECT i.Item_ID, i.Condition, m.Model_Name, s.length_cm, s.type, s.bindings "
								   + "FROM items i "
								   + "INNER JOIN skis s "
								   + "ON i.item_id = s.item_id "
								   + "INNER JOIN models m "
								   + "ON i.model_id = m.model_id "
								   + "WHERE i.Out_Of_Service = 'f' "
								   + "AND NOT i.Condition = 'Poor') "
								   + "EXCEPT "
								   + "(SELECT i.Item_ID, i.Condition, m.Model_Name, s.length_cm, s.type, s.bindings "
								   + "FROM itemrentals it "
								   + "INNER JOIN rentalorders re "
								   + "ON it.rental_order_id = re.rental_order_id "
								   + "INNER JOIN items i "
								   + "ON it.item_id = i.item_id "
								   + "INNER JOIN skis s "
								   + "ON it.item_id = s.item_id "
								   + "INNER JOIN models m "
								   + "ON i.model_id = m.model_id "
								   + "WHERE re.date_in IS NULL);";
		return jdbc.query(getSkisForRentQuery);
	}

	public static String insertRentalOrder(RentalOrderOptions rentalOrderOpts) {
		JDBC jdbc = JDBC.getInstance();
		String insertRentalOrderQuery = "INSERT INTO RentalOrders (employee_id, customer_id, date_out, total_price) "
									  + "VALUES ('" + rentalOrderOpts.getEmployeeID()
									  + "', '" + rentalOrderOpts.getCustomerID()
									  + "', '" + rentalOrderOpts.getDateOut()
				 					  + "', '" + rentalOrderOpts.getTotalPrice() + "');";
		return jdbc.update(insertRentalOrderQuery);
	}

	public static int getRentalOrderID(RentalOrderOptions rentalOrderOpts) {
		JDBC jdbc = JDBC.getInstance();
		String getRentalOrderIDQuery = "SELECT rental_order_id FROM RentalOrders WHERE "
										+ "employee_id = '" + rentalOrderOpts.getEmployeeID()
										+ "' AND customer_id = '" + rentalOrderOpts.getCustomerID()
										+ "' AND date_out = '" + rentalOrderOpts.getDateOut()
										+ "' AND total_price = '" + rentalOrderOpts.getTotalPrice() + "';";

		ResultSet rs = jdbc.query(getRentalOrderIDQuery);

		int rental_order_id = -1;
		try {
			while (rs.next()) {
				rental_order_id = (int) rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rental_order_id;
	}

	public static String addItemsToRentalOrder(ArrayList<Integer> itemsToBeRented, int rentalOrderID) {
		JDBC jdbc = JDBC.getInstance();
		String addItemsToRentalOrderQuery = "INSERT INTO itemrentals (rental_order_id, item_id) VALUES ";

		for (int i = 0; i < itemsToBeRented.size(); i++) {
			if (i != itemsToBeRented.size() - 1) {
				addItemsToRentalOrderQuery += "('" + rentalOrderID + "', '" + itemsToBeRented.get(i) + "'), ";
			} else {
				addItemsToRentalOrderQuery += "('" + rentalOrderID + "', '" + itemsToBeRented.get(i) + "');";
			}
		}
		return jdbc.update(addItemsToRentalOrderQuery);
	}

	public static ResultSet getEmployees() {
		JDBC jdbc = JDBC.getInstance();
		String getEmployeesQuery = "SELECT employee_id, name, address, type FROM employees;";
		return jdbc.query(getEmployeesQuery);
	}

    public static ResultSet getCustomers(CustomerOptions options) {
    	JDBC jdbc = JDBC.getInstance();
    	String customersQuery = "SELECT C.Customer_ID, C.Name, C.Address, C.Phone_Number, HRO.Has_Rental_Order, count(RO.Rental_Order_ID) AS Total_Rentals, sum(RO.Total_Price::decimal) AS Total_Amount_Paid, C.Age, C.Weight_kg, C.Height_cm, C.Credit_Card_Number, BI.Type, BI.Billing_Address, BI.CVV "
    							+ "FROM Customers C "
    							+ "INNER JOIN BillingInformation BI ON BI.Credit_Card_Number = C.Credit_Card_Number "
    							+ "LEFT JOIN RentalOrders RO ON C.Customer_ID = RO.Customer_ID "
    							+ "INNER JOIN ("
    							+ "(SELECT C.Customer_ID, 'Yes' AS Has_Rental_Order FROM RentalOrders RO LEFT JOIN Customers C ON C.Customer_ID = RO.Customer_ID AND C.Customer_ID IS NOT NULL GROUP BY C.Customer_ID)"
    							+ " UNION "
    							+ "(SELECT C.Customer_ID, 'No' AS Has_Rental_Order FROM Customers C LEFT JOIN RentalOrders RO ON C.Customer_ID = RO.Customer_ID WHERE RO.Customer_ID IS NULL GROUP BY C.Customer_ID)"
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
    	if(!options.getAge().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "C.Age LIKE '%" + options.getAge() + "%' ";
    	}
    	if(!options.getPhone().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "C.Phone_Number LIKE '%" + options.getPhone() + "%' ";
    	}
    	if(!options.getAddress().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "C.Address LIKE '%" + options.getAddress() + "%' ";
    	}
    	if(!options.getWeight().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "C.Weight_kg LIKE '%" + options.getWeight() + "%' ";
    	}
    	if(!options.getHeight().equals("")) {
    		if(!whereClause.equals("")) {
    			whereClause += "AND ";
    		}
    		whereClause += "C.Height_cm LIKE '%" + options.getHeight() + "%' ";
    	}
    	if(!whereClause.equals("")) {
			whereClause = "WHERE " + whereClause;
		}    	
    	customersQuery += whereClause;
    	
    	// GROUP BY
    	customersQuery += "GROUP BY C.Customer_ID, BI.Credit_Card_Number, HRO.Has_Rental_Order ";
    	
    	// ORDERING
    	switch(options.getOrdering()) {
    	case NAME:
    		customersQuery += "ORDER BY C.Name";
    		break;
    	case ADDRESS:
    		customersQuery += "ORDER BY C.Address";
    		break;
    	case PHONE:
    		customersQuery += "ORDER BY C.Phone_Number";
    		break;
    	case AGE:
    		customersQuery += "ORDER BY C.Age";
    		break;
    	case WEIGHT:
    		customersQuery += "ORDER BY C.Weight_kg";
    		break;
    	case HEIGHT:
    		customersQuery += "ORDER BY C.Height_cm";
    		break;
    	case RENTAL_STATUS:
    		customersQuery += "ORDER BY HRO.Has_Rental_Order";
    		break;
    	case TOTAL_RENTALS:
    		customersQuery += "ORDER BY Total_Rentals";
    		break;
    	case TOTAL_AMOUNT_PAID:
    		customersQuery += "ORDER BY Total_Amount_Paid";
    		break;
    	}
    	
    	customersQuery += ";";
        System.out.println(customersQuery);
    	ResultSet rs = jdbc.query(customersQuery);
    	
        return rs;
    }
}
