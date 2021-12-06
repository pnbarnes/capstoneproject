package Entity;


import java.sql.Date;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class Employee {
    private int employeeID;
    private String firstName;
    private String lastName;
    private Boolean isManager;
    private Date hireDate;
    private long hourlyPay;
    private int storeID;

    public Employee(){}

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean manager) {
        isManager = manager;
    }
}
