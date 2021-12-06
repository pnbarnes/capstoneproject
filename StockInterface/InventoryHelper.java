package StockInterface;

import Entity.InventoryItem;
import Entity.Store;
import Util.dbConnection;

import java.sql.*;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class InventoryHelper {
    private Connection con = null;

    public InventoryHelper(){
        try {
            this.con = dbConnection.getConnection();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        if (this.con == null){
            System.exit(1);
        }
    }


    private final String searchInventory = "Select * from inventory.inventoryitems where UPC = ?";
    private final String updateInventoryItem = "UPDATE inventory.inventoryitems set QuantityAvail = ? where InventoryItemID = ?";
    private final String addInventoryItem = "Insert into inventory.inventoryitems values(?, ?, ?, ?, ?)";
    private final String insertSale = "insert into inventory.saleitems (InventoryItemID, StoreID, quantitySold, SaleDate) values(?, ?, ?, ?)";


    public Boolean isNewItem(String UPC) throws SQLException {

        boolean isNew = true;


        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchInventory);
        stmt.setString(1, UPC);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            isNew = false;
        }
        rs.close();
        con.close();
        return isNew;

    }

    public void updateInventory(InventoryItem item) throws SQLException {
        int newQuantity = item.getQuantityOnHand();
        int itemId = item.getItemId();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(updateInventoryItem);
        stmt.setInt(1, newQuantity);
        stmt.setInt(2, itemId);
        int updatedRows = stmt.executeUpdate();

        if (updatedRows == 1) {
            System.out.print("Quantity updated for UPC: " + item.getUPC().toString());
        } else {
            System.out.print("Failed to update row for UPC: " + item.getUPC().toString());
        }

        con.close();
    }

    public void addToInventory(InventoryItem item) throws SQLException {

        String size = item.getSize();
        String UPC = item.getUPC();
        String color = item.getColor();
        int quantity = item.getQuantityOnHand();
        int storeId = item.getStoreId();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(addInventoryItem);
        stmt.setString(1, size);
        stmt.setString(2, UPC);
        stmt.setString(3, color);
        stmt.setInt(4, quantity);
        stmt.setInt(5, storeId);
        stmt.execute();

        if(!isNewItem(item.getUPC())){
            System.out.print("Inventory updated for " + UPC);
        }
        con.close();
    }

    public void insertSale(InventoryItem item, Store store, int quantitySold) throws SQLException {

        int itemId = item.getItemId();
        int storeId = store.getStoreID();

        Date saleDate = new Date(System.currentTimeMillis());

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(insertSale);
        stmt.setInt(1, itemId);
        stmt.setInt(2, storeId);
        stmt.setInt(3, quantitySold);
        stmt.setDate(4, saleDate);

        stmt.execute();

        con.close();

    }
}

