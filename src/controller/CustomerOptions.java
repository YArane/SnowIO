package controller;

public class CustomerOptions {

    private String customerName;
    private String customerAddress;
    private String customerAge;
    private String customerPhone;
    private String weight;
    private String height;
    private String creditCardNumber;
    private String creditCardType;
    private String cardholderName;
    private String billingAddress;
    private String CVV;

    public CustomerOptions() {}

    public CustomerOptions(String customerName, String customerAddress, String customerAge,
                           String customerPhone, String weight, String height, String creditCardNumber,
                           String creditCardType, String cardholderName, String billingAddress,
                           String CVV) {

        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerAge = customerAge;
        this.customerPhone = customerPhone;
        this.weight = weight;
        this.height = height;
        this.creditCardNumber = creditCardNumber;
        this.creditCardType = creditCardType;
        this.cardholderName = cardholderName;
        this.billingAddress = billingAddress;
        this.CVV = CVV;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
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

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

}
