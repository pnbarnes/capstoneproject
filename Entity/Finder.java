package Entity;

import Util.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class Finder {
    // basic finder classes for use in ISMS

    private final String searchInventory = "Select * from inventory.inventoryitems where UPC = ?";
    private final String searchStore = "Select * from inventory.stores where StoreID = ?";
    private final String searchManager = "Select * from inventory.employees where IsManager = 1";
    private final String searchCustomer = "Select * from inventory.customers where LastName = ? and Phone = ?";
    private final String searchCustomerById = "Select * from inventory.customers where CustomerID = ?";
    private final String searchCustomerByRewards = "Select * from inventory.customers where RewardsNumber = ?";


    public InventoryItem itemFinder (String UPC) throws SQLException {

        InventoryItem item = new InventoryItem();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchInventory);
        stmt.setString(1, UPC);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            item.setItemId(rs.getInt("InventoryItemID"));
            item.setColor(rs.getString("Color"));
            item.setSize(rs.getString("Size"));
            item.setUPC(rs.getString("UPC"));
            item.setQuantityOnHand(rs.getInt("QuantityAvail"));
            item.setStoreId(rs.getInt("StoreID"));
            item.setItemName(rs.getString("ItemName"));

            return item;
        }
        rs.close();
        con.close();
        return null;
    }

    public InventoryItem itemFinder (int inventoryID) throws SQLException {

        InventoryItem item = new InventoryItem();
        String searchById = "Select * from inventory.inventoryitems where InventoryItemID = ?";

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchById);
        stmt.setInt(1, inventoryID);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            item.setItemId(rs.getInt("InventoryItemID"));
            item.setColor(rs.getString("Color"));
            item.setSize(rs.getString("Size"));
            item.setUPC(rs.getString("UPC"));
            item.setQuantityOnHand(rs.getInt("QuantityAvail"));
            item.setStoreId(rs.getInt("StoreID"));
            item.setItemName(rs.getString("ItemName"));

            rs.close();
            con.close();
            return item;

        }
        else{
            return null;
        }


    }




    public Store storeFinder (int storeId) throws SQLException {

        Store store = new Store();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchStore);
        stmt.setInt(1, storeId);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            store.setStoreID(rs.getInt("StoreID"));
            store.setStoreType(rs.getString("StoreType"));
            store.setName(rs.getString("StoreName"));
            store.setZipCode(rs.getLong("ZipCode"));
        }
        rs.close();
        con.close();

        return store;
    }
    public Customer customerFinder (int customerId) throws SQLException {

        Customer customer = new Customer();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchCustomerById);
        stmt.setInt(1, customerId);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            customer.setCustomerID(rs.getInt("CustomerID"));
            customer.setFirstName(rs.getString("FirstName"));
            customer.setLastName(rs.getString("LastName"));
            customer.setPhoneNumber(rs.getString("Phone"));
            customer.setEmailAddress(rs.getString("Email"));
            customer.setRewardsNumber(rs.getString("RewardsNumber"));

            if(rs.getInt("hasStoreCredit") == 1){
                customer.setHasStoreCredit(true);
            }
            else{
                customer.setHasStoreCredit(false);
            }

        }
        rs.close();
        con.close();

        return customer;
    }

    public Customer customerFinder (String lastName, String phone) throws SQLException {


        Customer customer = new Customer();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchCustomer);
        stmt.setString(1, lastName);
        stmt.setString(2, phone);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            customer.setCustomerID(rs.getInt("CustomerID"));
            customer.setFirstName(rs.getString("FirstName"));
            customer.setLastName(rs.getString("LastName"));
            customer.setPhoneNumber(rs.getString("Phone"));
            customer.setEmailAddress(rs.getString("Email"));
            customer.setRewardsNumber(rs.getString("RewardsNumber"));

            if(rs.getInt("hasStoreCredit") == 1){
                customer.setHasStoreCredit(true);
            }
            else{
                customer.setHasStoreCredit(false);
            }

        }
        rs.close();
        con.close();

        return customer;
    }

    public Customer customerFinder (String rewardsNum) throws SQLException {


        Customer customer = new Customer();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchCustomerByRewards);
        stmt.setString(1, rewardsNum);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            customer.setCustomerID(rs.getInt("CustomerID"));
            customer.setFirstName(rs.getString("FirstName"));
            customer.setLastName(rs.getString("LastName"));
            customer.setPhoneNumber(rs.getString("Phone"));
            customer.setEmailAddress(rs.getString("Email"));
            customer.setRewardsNumber(rs.getString("RewardsNumber"));

            if(rs.getInt("hasStoreCredit") == 1){
                customer.setHasStoreCredit(true);
            }
            else{
                customer.setHasStoreCredit(false);
            }

        }
        rs.close();
        con.close();

        return customer;
    }



    public Manager managerFinder (int managerId) throws SQLException {

        Manager manager = new Manager();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchManager);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            manager.setEmployeeID(rs.getInt("EmployeeID"));
            manager.setFirstName(rs.getString("FirstName"));
            manager.setLastName(rs.getString("LastName"));
            manager.setIsManager(true);
            manager.setEmail(rs.getString("Email"));
            manager.setStoreID(rs.getInt("StoreID"));


        }
        rs.close();
        con.close();

        return manager;
    }
    private final String selectEmployee = "select * from inventory.employees where employeeID = ?";

    public Employee employeeFinder(int employeeId) throws SQLException {
        Employee employee = new Employee();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectEmployee);
        stmt.setInt(1, employeeId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            employee.setEmployeeID(rs.getInt("EmployeeID"));
            employee.setFirstName(rs.getString("FirstName"));
            employee.setLastName(rs.getString("LastName"));
            employee.setStoreID(rs.getInt("StoreID"));
            if(rs.getInt("isManager") == 1){
                employee.setIsManager(true);
            }
            else{
                employee.setIsManager(false);
            }
            return employee;
        }
        rs.close();
        con.close();

        return employee;
    }

}
