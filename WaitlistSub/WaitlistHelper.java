package WaitlistSub;

import Entity.*;
import StockInterface.InventoryHelper;
import Util.EmailNotificationUtil;
import Util.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/25
 */
public class WaitlistHelper {

    Finder finder = new Finder();
    EmailNotificationUtil emailUtil = new EmailNotificationUtil();
    String msg;
    InventoryHelper inventoryHelper = new InventoryHelper();

    public String notifyManager(WaitlistItem item) throws SQLException {

        Manager manager = finder.managerFinder(item.getEmployeeId());
        Customer customer = finder.customerFinder(item.getWaitlistCustomer());

       /* String itemInfo = "<h2>Item Info</h2><p>" + item + "</p>";
        String customerInfo = "<h2>Customer Info</h2><p>" + customer.toString() + "</p>";
*/
        String messageContent = "Waitlist Item Received, manager notified";

        String to = manager.getEmail();
        String from = "ISMSWaitlist@eddiebauer.com";
        String subject = "Waitlist Item Received Notification";

        if (emailUtil.sendEmail(subject, messageContent, to, from)) {
            msg = messageContent;
        }
        else{
            msg = "Message error occurred";
        }

        return msg;

    }

    // adds waitlist item to database

    public void addWaitlistItem(WaitlistItem waitlistItem, Customer customer, Employee employee) throws SQLException{

        InventoryItem inventoryItem = finder.itemFinder(waitlistItem.getUPC());
        WaitlistItem itemToAdd = waitlistItem;

        // these methods find the corresponding inventory itemID for the waitlist item
        if(!inventoryItem.getUPC().isEmpty()){
            // if item exists in database, set item id on waitlist item to match
            itemToAdd.setItemId(inventoryItem.getItemId());

            String insertWaitlistItem = "insert into inventory.waitlistitems(DateAdded, Quantity, InventoryItemID, CustomerID, EmployeeID) values (?, ?, ?, ?, ?)";


            Date dateAdded = new Date(System.currentTimeMillis());
            int desiredQuantity = itemToAdd.getDesiredQuantity();
            int inventoryItemId = itemToAdd.getItemId();
            int customerId = addCustomer(customer);
            int employeeId = employee.getEmployeeID();

            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertWaitlistItem);
            stmt.setDate(1, dateAdded);
            stmt.setInt(2, desiredQuantity);
            stmt.setInt(3, inventoryItemId);
            stmt.setInt(4, customerId);
            stmt.setInt(5, employeeId);

            stmt.execute();
            con.close();
        }
        else{
            msg = "Item is not in inventory. Please enter a valid item";
        }


    }


    // adds customer to waitlist item and database

    public int addCustomer(Customer customer) throws SQLException {

        // checks if customer already exists in database
        Customer foundCustomer = finder.customerFinder(customer.getLastName(), customer.getPhoneNumber());

        if (foundCustomer.getPhoneNumber() == customer.getPhoneNumber() && foundCustomer.getLastName() == customer.getLastName()) {
            // customer exists

            return foundCustomer.getCustomerID();

        } else {
            // customer does not exist, add it to database

            String insertCustomer = "insert into inventory.customers(FirstName, LastName, Phone, Email, hasStoreCredit) values (?, ?, ?, ?, ?)";

            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            String phone = customer.getPhoneNumber();
            String email = customer.getEmailAddress();


            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertCustomer);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setInt(5, 0);


            stmt.execute();
            con.close();

            Customer newCustomer = finder.customerFinder(lastName, phone);
            return newCustomer.getCustomerID();
        }
    }

    // find waitlist items by customer
    public ObservableList<WaitlistItem> findWaitlistItems(String phoneNumber, String lastName) throws SQLException{

        String selectByCustomer = "Select * from inventory.waitlistitems wl" +
                " inner join inventory.customers cust on wl.CustomerID = cust.CustomerID" +
                " where cust.LastName = ? and cust.Phone = ?";
        ObservableList<WaitlistItem> waitlistItems = FXCollections.observableArrayList();

        // general database connection

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectByCustomer);
        stmt.setString(1, lastName);
        stmt.setString(2, phoneNumber);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            WaitlistItem waitlistItem = new WaitlistItem();
            waitlistItem.setWaitlistItemID(rs.getInt("WaitlistItemID"));
            waitlistItem.setWaitlistStart(rs.getDate("DateAdded"));
            waitlistItem.setDesiredQuantity(rs.getInt("Quantity"));
            waitlistItem.setItemId(rs.getInt("InventoryItemID"));
            waitlistItem.setWaitlistCustomer(rs.getInt("CustomerID"));
            waitlistItem.setEmployeeId(rs.getInt("EmployeeID"));

            waitlistItems.add(waitlistItem);

        }

        // adds extra details to each item in the list

        for(WaitlistItem item : waitlistItems){
            InventoryItem inv = finder.itemFinder(item.getItemId());
            item.setUPC(inv.getUPC());
            item.setSize(inv.getSize());
            item.setColor(inv.getColor());
            item.setQuantityOnHand(inv.getQuantityOnHand());
            item.setItemName(inv.getItemName());

        }

        rs.close();
        con.close();
        return waitlistItems;


    }

    // find waitlist items by UPC
    public ObservableList<WaitlistItem> findWaitlistItems(String UPC) throws SQLException{

        int inventoryItemId = finder.itemFinder(UPC).getItemId();


        String selectByCustomer = "Select * from inventory.waitlistitems where inventoryItemId = ?";
        ObservableList<WaitlistItem> waitlistItems = FXCollections.observableArrayList();

        // general database connection

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectByCustomer);
        stmt.setInt(1, inventoryItemId);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            WaitlistItem waitlistItem = new WaitlistItem();
            waitlistItem.setWaitlistItemID(rs.getInt("WaitlistItemID"));
            waitlistItem.setWaitlistStart(rs.getDate("DateAdded"));
            waitlistItem.setDesiredQuantity(rs.getInt("Quantity"));
            waitlistItem.setItemId(rs.getInt("InventoryItemID"));
            waitlistItem.setWaitlistCustomer(rs.getInt("CustomerID"));
            waitlistItem.setEmployeeId(rs.getInt("EmployeeID"));

            waitlistItems.add(waitlistItem);

        }

        // adds extra details to each item in the list

        for(WaitlistItem item : waitlistItems){
            InventoryItem inv = finder.itemFinder(item.getItemId());
            item.setUPC(inv.getUPC());
            item.setItemName(inv.getItemName());
            item.setSize(inv.getSize());
            item.setColor(inv.getColor());
            item.setQuantityOnHand(inv.getQuantityOnHand());

        }

        rs.close();
        con.close();
        return waitlistItems;


    }

    // find waitlist items by date range

    public ObservableList<WaitlistItem> findWaitlistItems(Date startDate, Date endDate) throws SQLException{

        String selectByCustomer = "Select * from inventory.waitlistitems where DateAdded between ? and ?";
        ObservableList<WaitlistItem> waitlistItems = FXCollections.observableArrayList();

        // general database connection

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectByCustomer);
        stmt.setDate(1, startDate);
        stmt.setDate(2, endDate);


        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            WaitlistItem waitlistItem = new WaitlistItem();
            waitlistItem.setWaitlistItemID(rs.getInt("WaitlistItemID"));
            waitlistItem.setWaitlistStart(rs.getDate("DateAdded"));
            waitlistItem.setDesiredQuantity(rs.getInt("Quantity"));
            waitlistItem.setItemId(rs.getInt("InventoryItemID"));
            waitlistItem.setWaitlistCustomer(rs.getInt("CustomerID"));
            waitlistItem.setEmployeeId(rs.getInt("EmployeeID"));

            waitlistItems.add(waitlistItem);

        }

        // adds extra details to each item in the list

        for(WaitlistItem item : waitlistItems){
            InventoryItem inv = finder.itemFinder(item.getItemId());
            item.setUPC(inv.getUPC());
            item.setSize(inv.getSize());
            item.setColor(inv.getColor());
            item.setQuantityOnHand(inv.getQuantityOnHand());
            item.setItemName(inv.getItemName());

        }

        rs.close();
        con.close();
        return waitlistItems;


    }


    public void deleteFromWaitlist(WaitlistItem item) throws SQLException {

        String deleteWaitlistItem = "delete from inventory.waitlistitems where WaitlistItemID = ?";

        // general database connection

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(deleteWaitlistItem);
        stmt.setInt(1, item.getWaitlistItemID());

        stmt.execute();

    }
}


