package controller;

public class CustomerOptions {
	public enum Ordering {
		NAME,
		ADDRESS,
		PHONE,
		AGE,
		WEIGHT,
		HEIGHT,
		RENTAL_STATUS,
		TOTAL_RENTALS,
		TOTAL_AMOUNT_PAID,
	};
	
	public enum RentalStatus {
		ALL,
		CURRENTLY_RENTING,
		NOT_CURRENTLY_RENTING,
		
	}
	
	private Ordering ordering;
	private String name;
	private String address;
	private String phone;
	private String credit_card_number;
	private String age;
	private String weight;
	private String height;
	private RentalStatus rentalStatus;
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	public CustomerOptions() {
		ordering = Ordering.NAME;
		name = "";
		address = "";
		phone = "";
		credit_card_number = "";
		age = "";
		weight = "";
		height = "";
		rentalStatus = RentalStatus.ALL;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreditCardNumber() {
		return credit_card_number;
	}

	public void setCreditCardNumber(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}

	public RentalStatus getRentalStatus() {
		return rentalStatus;
	}

	public void setRentalStatus(RentalStatus rentalStatus) {
		this.rentalStatus = rentalStatus;
	}

	public void setOrdering(String o){
	    switch(o){
	        case "name":
	            setOrdering(Ordering.NAME); 
	            break;
	        case "address":
	            setOrdering(Ordering.ADDRESS); 
	            break;
	        case "rental_status":
	            setOrdering(Ordering.RENTAL_STATUS); 
	            break;
	        case "total_rentals":
	            setOrdering(Ordering.TOTAL_RENTALS); 
	            break;
	        case "total_amount_paid":
	        	setOrdering(Ordering.TOTAL_AMOUNT_PAID);
	        	break;
	    }
	}
}
