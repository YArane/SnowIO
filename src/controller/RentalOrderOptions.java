package controller;

public class RentalOrderOptions {
	public enum Ordering {
		CUSTOMER,
		EMPLOYEE,
		TOTAL_PRICE,
		DATE_IN,
		DATE_OUT,
	};
	
	private Ordering ordering;
	private String employee_name;
	private String customer_name;
	
	public RentalOrderOptions() {
		ordering = Ordering.DATE_IN;
		employee_name = "";
		customer_name = "";
	}
	
	public void setOrdering(Ordering o) {
		ordering = o;
	}
	
	public void setEmployeeName(String e) {
		employee_name = e;
	}
	
	public void setCustomerName(String c) {
		customer_name = c;
	}
	
	public Ordering getOrdering() {
		return ordering;
	}
	
	public String getEmployeeName() {
		return employee_name;
	}
	
	public String getCustomerName() {
		return customer_name;
	}
}
