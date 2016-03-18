package controller;

public class BillingInfoOptions {
	public enum Ordering {
		NAME,
		CREDIT_CARD_NUMBER,
		TYPE,
		ADDRESS,
		CVV,
	};
	
	private Ordering ordering;
	private String name;
	private String address;
	private String type;
	private String credit_card_number;
	private String cvv;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public BillingInfoOptions() {
		ordering = Ordering.NAME;
		name = "";
		address = "";
		type = "";
		credit_card_number = "";
	}
	
	public Ordering getOrdering() {
		return ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreditCardNumber() {
		return credit_card_number;
	}
	
	public String getCVV() {
		return cvv;
	}
	
	public void setCVV(String cvv) {
		this.cvv = cvv;
	}

	public void setCreditCardNumber(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}

	public void setOrdering(String o){
	    switch(o){
	        case "name":
	            setOrdering(Ordering.NAME); 
	            break;
	        case "address":
	            setOrdering(Ordering.ADDRESS); 
	            break;
	        case "type":
	            setOrdering(Ordering.TYPE); 
	            break;
	        case "cvv":
	            setOrdering(Ordering.CVV); 
	            break;
	        case "credit_card_number":
	        	setOrdering(Ordering.CREDIT_CARD_NUMBER);
	        	break;
	    }
	}
}
