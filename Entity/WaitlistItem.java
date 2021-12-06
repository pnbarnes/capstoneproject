package Entity;

import java.sql.Date;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class WaitlistItem extends InventoryItem {

    private int waitlistItemID;
    private Date waitlistStart;
    private int desiredQuantity;
    private String managerEmail;
    private int customerId;
    private int employeeId;

    public WaitlistItem(){

    }

    
    public int getWaitlistCustomer() {
        return customerId;
    }


    public void setWaitlistCustomer(int customerId) {
        this.customerId = customerId;
    }


    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public Date getWaitlistStart() {
        return waitlistStart;
    }
    public int getDesiredQuantity() {
        return desiredQuantity;
    }
    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }
    public void setWaitlistStart(Date waitlistStart) {
        this.waitlistStart = waitlistStart;
    }

    public int getWaitlistItemID() {
        return waitlistItemID;
    }

    public void setWaitlistItemID(int waitlistItemID) {
        this.waitlistItemID = waitlistItemID;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString(){
        return "Item UPC: " + this.getUPC() + "\n" +
                "Size: " + this.getSize() + "\n" +
                "Color: " + this.getColor() + "\n" +
                "Desired Quantity: " + this.getDesiredQuantity() + "\n" +
                "Added By Employee ID: " + this.getEmployeeId() + "\n" +
                "Added Date: " + this.getWaitlistStart() + "\n";
    }
}