package Entity;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String rewardsNumber;
    private Boolean hasStoreCredit;

    public Customer() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRewardsNumber() {
        return rewardsNumber;
    }

    public void setRewardsNumber(String rewardsNumber) {
        this.rewardsNumber = rewardsNumber;
    }

    public Boolean getHasStoreCredit() {
        return hasStoreCredit;
    }

    public void setHasStoreCredit(Boolean hasStoreCredit) {
        this.hasStoreCredit = hasStoreCredit;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

   


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString(){
        return "Customer Name: " + this.getFirstName() + " " + this.getLastName() + "\n" +
                "Phone: " + this.getPhoneNumber() + "\n" +
                "Email: " + this.getEmailAddress() + "\n" +
                "Rewards Number: " + this.getRewardsNumber() + "\n" +
                "Has Store Credit: " + this.getHasStoreCredit() + "\n";
    }
}