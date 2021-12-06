package BackstockSub;

import Entity.InventoryItem;
import Util.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */

public class BackstockHelper {

    private Connection con = null;

    // constructor
    public BackstockHelper(){
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

    private final String selectBackstock = "select size, upc, color, quantityAvail, storeName, phoneNumber, zipcode from inventory.inventoryitems items inner join inventory.stores stores on items.StoreID = stores.StoreID where UPC = ?";
    private final String whereStoreClause = " and items.storeID = ?";

    public ObservableList<InventoryItem> getBackstock(String UPC, int storeId) throws SQLException {
        //https://docs.oracle.com/javase/8/javafx/api/javafx/collections/FXCollections.html

        ObservableList<InventoryItem> backstockItems = FXCollections.observableArrayList();

        // general database connection

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectBackstock + whereStoreClause);
        stmt.setString(1, UPC);
        stmt.setInt(2, storeId);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            InventoryItem item = new InventoryItem();

            item.setSize(rs.getString("size"));
            item.setUPC(rs.getString("UPC"));
            item.setColor(rs.getString("color"));
            item.setQuantityOnHand(rs.getInt("quantityAvail") - 1);
            item.setStoreName(rs.getString("storeName"));
            item.setPhoneNumber(rs.getString("phoneNumber"));
            item.setZipCode(rs.getString("zipcode"));

            backstockItems.add(item);

        }
        rs.close();
        con.close();
        return backstockItems;
    }

    // overloaded method for if there's states
    public ObservableList<InventoryItem> getBackstock(String UPC, String state) throws SQLException {

        // https://docs.oracle.com/javase/8/javafx/api/javafx/collections/FXCollections.html
        ObservableList<InventoryItem> backstockItems = FXCollections.observableArrayList();

        ArrayList<String> zipcodeArray = getZipCodes(state);

        String whereZipcodeClause = buildWhereClause(zipcodeArray);

        String sql = selectBackstock + whereZipcodeClause;

        if(!zipcodeArray.isEmpty()) {

            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, UPC);
            int statementCount = 2;

            for(int i = 0; i < zipcodeArray.size(); i++){

                stmt.setString(statementCount, zipcodeArray.get(i).toString());
                statementCount++;
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InventoryItem item = new InventoryItem();

                item.setSize(rs.getString("size"));
                item.setUPC(rs.getString("UPC"));
                item.setColor(rs.getString("color"));
                item.setQuantityOnHand(rs.getInt("quantityAvail") - 1);
                item.setStoreName(rs.getString("storeName"));
                item.setPhoneNumber(rs.getString("phoneNumber"));
                item.setZipCode(rs.getString("zipcode"));

                backstockItems.add(item);

            }
            rs.close();
            con.close();
        }


        return backstockItems;
    }

    public ArrayList<String> getZipCodes(String state) throws SQLException {

        String sql = "select ZipCode from inventory.zipcodes where state = ?";
        ArrayList<String> zipcodes = new ArrayList<String>();


        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, state);
        ResultSet rs = stmt.executeQuery();


        while (rs.next()){
            zipcodes.add(rs.getString("ZipCode"));
        }


        return zipcodes;
    }

    public String buildWhereClause(ArrayList<String> zipcodeArray) {
        // determines if there are more than one zip codes to search and changes clause accordingly

        StringBuilder whereZipcodeClause = new StringBuilder();

        if (zipcodeArray.size() > 1) {

            StringBuilder zipPlaceholders = new StringBuilder();

            for (int i = 1; i <= zipcodeArray.size(); i++) {
                zipPlaceholders.append("?");
                if (i < zipcodeArray.size()) {
                    zipPlaceholders.append(", ");
                }
            }
            whereZipcodeClause.append(" and stores.zipCode in (");
            whereZipcodeClause.append(zipPlaceholders.toString());
            whereZipcodeClause.append(")");
        } else {
            whereZipcodeClause.append(" and stores.zipCode = ?");
        }

        return whereZipcodeClause.toString();
    }

}
