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
	private boolean pastOrders;
	private boolean currentOrders;
	
	public RentalOrderOptions() {
		ordering = Ordering.DATE_IN;
		employee_name = "";
		customer_name = "";
		pastOrders = true;
		currentOrders = true;
	}
	
	public boolean getCurrentOrders() {
		return currentOrders;
	}

	public void setCurrentOrders(boolean currentOrders) {
		this.currentOrders = currentOrders;
	}
	
	public void toggleCurrentOrders(){
        this.currentOrders = !this.currentOrders;
    }

	public boolean getPastOrders() {
		return pastOrders;
	}

	public void setPastOrders(boolean pastOrders) {
		this.pastOrders = pastOrders;
	}
	
public void togglePastOrders(){
	    this.pastOrders = !this.pastOrders;
	}

	public void setOrdering(Ordering o) {
		ordering = o;
	}
	public void setOrdering(String o){
	    switch(o){
	        case "customer_name":
	            setOrdering(Ordering.CUSTOMER); 
	            break;
	        case "employee_name":
	            setOrdering(Ordering.EMPLOYEE); 
	            break;
	        case "date_in":
	            setOrdering(Ordering.DATE_IN); 
	            break;
	        case "date_out":
	            setOrdering(Ordering.DATE_OUT); 
	            break;
	        case "total_price":
               setOrdering(Ordering.TOTAL_PRICE); 
               break;
	        default:
	            setOrdering(ordering);
	    }
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
