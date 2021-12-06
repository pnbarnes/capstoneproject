package ReportingSub;

import Entity.ReportItem;
import Util.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 05/21
 */
public class SalesTrendHelper {

    // also used for stock refill reporting
    public ObservableList<ReportItem> getBestsellingItemsByDate(Date begin, Date end, int storeId) throws SQLException {

        ObservableList<ReportItem> bestsellingItems = FXCollections.observableArrayList();

        String selectBestselling = "select sales.inventoryItemId, sales.storeId, size, UPC, color, quantityAvail, ItemName, sum(quantitySold) as QuantitySold" +
                " from saleitems sales inner join inventoryitems inv on sales.inventoryItemid = inv.inventoryitemid" +
                " where saleDate between ? and ?" +
                " and sales.storeId = ?" +
                " group by inventoryitemid order by sum(quantitySold) desc limit 100";

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectBestselling);
        stmt.setDate(1, begin);
        stmt.setDate(2, end);
        stmt.setInt(3, storeId);


        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            ReportItem reportItem = new ReportItem();

            reportItem.setItemId(rs.getInt("inventoryItemId"));
            reportItem.setStoreId(rs.getInt("storeId"));
            reportItem.setUPC(rs.getString("UPC"));
            reportItem.setItemName(rs.getString("ItemName"));
            reportItem.setSize(rs.getString("size"));
            reportItem.setColor(rs.getString("color"));
            reportItem.setQuantityOnHand(rs.getInt("quantityAvail"));
            reportItem.setQuantitySold(rs.getInt("QuantitySold"));

            bestsellingItems.add(reportItem);

        }

        rs.close();
        con.close();
        return bestsellingItems;

    }

    public ObservableList<ReportItem> getBestsellingItemsByGender(Date begin, Date end, String gender, int storeId) throws SQLException {

        ObservableList<ReportItem> bestsellingItems = FXCollections.observableArrayList();
        int stmtCount = 0;
        Connection con = dbConnection.getConnection();;
        PreparedStatement stmt;
        String selectBestselling = "select sales.inventoryItemId, sales.storeId, size, UPC, color, quantityAvail, ItemName, sum(quantitySold) as QuantitySold" +
                " from saleitems sales inner join inventoryitems inv on sales.inventoryItemid = inv.inventoryitemid" +
                " where saleDate between ? and ?" +
                " and inv.gender = ?" +
                " and sales.storeId = ?" +
                " group by inventoryitemid order by sum(quantitySold) desc limit 100";

        System.out.print(selectBestselling);
        stmt = con.prepareStatement(selectBestselling);
        stmt.setDate(1, begin);
        stmt.setDate(2, end);
        stmt.setString(3, gender);
        stmt.setInt(4, storeId);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            ReportItem reportItem = new ReportItem();

            reportItem.setItemId(rs.getInt("inventoryItemId"));
            reportItem.setStoreId(rs.getInt("storeId"));
            reportItem.setUPC(rs.getString("UPC"));
            reportItem.setItemName(rs.getString("ItemName"));
            reportItem.setSize(rs.getString("size"));
            reportItem.setColor(rs.getString("color"));
            reportItem.setQuantityOnHand(rs.getInt("quantityAvail"));
            reportItem.setQuantitySold(rs.getInt("QuantitySold"));

            bestsellingItems.add(reportItem);

        }

        rs.close();
        con.close();
        return bestsellingItems;

    }
}
