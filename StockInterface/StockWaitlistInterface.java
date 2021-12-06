package StockInterface;

import Entity.InventoryItem;
import Entity.WaitlistItem;
import Util.dbConnection;
import WaitlistSub.WaitlistHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class StockWaitlistInterface {

    WaitlistHelper waitlistHelper = new WaitlistHelper();
    public Boolean inWaitlist = false;

    public String triggerWaitlistCheck(InventoryItem item)throws SQLException {
        String msg = null;
        WaitlistItem waitlistItem = findWaitlistItem(item);
        if(inWaitlist){

            msg = waitlistHelper.notifyManager(waitlistItem);

        }

        return msg;

    }

    public WaitlistItem findWaitlistItem (InventoryItem item) throws SQLException {

        WaitlistItem waitlistItem = new WaitlistItem();
        String searchWaitlist = "Select * from inventory.waitlistitems where InventoryItemID = ?";

        int inventoryItemId = item.getItemId();

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(searchWaitlist);
        stmt.setInt(1, inventoryItemId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){

            waitlistItem.setWaitlistItemID(rs.getInt("WaitlistItemID"));
            waitlistItem.setWaitlistStart(rs.getDate("DateAdded"));
            waitlistItem.setDesiredQuantity(rs.getInt("Quantity"));
            waitlistItem.setItemId(inventoryItemId);
            waitlistItem.setWaitlistCustomer(rs.getInt("CustomerID"));
            waitlistItem.setEmployeeId(rs.getInt("EmployeeID"));

            if(waitlistItem.getDesiredQuantity() <= item.getQuantityOnHand()){
                inWaitlist = true;
            }

        }

        rs.close();
        con.close();

        return waitlistItem;

    }

}
