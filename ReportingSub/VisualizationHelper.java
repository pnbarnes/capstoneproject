package ReportingSub;

import Entity.ReportItem;
import Util.dbConnection;

import java.sql.*;
import java.util.ArrayList;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 05/21
 */

public class VisualizationHelper {

    public ArrayList<ReportItem> getItemsBySeason(Date begin, Date end, String gender, int storeId) throws SQLException {
        ArrayList<ReportItem> items = new ArrayList<ReportItem>();

        String selectBySeason = "SELECT season, sum(quantitySold) as QuantitySold" +
                " from saleitems sales inner join inventoryitems inv on sales.inventoryItemid = inv.inventoryitemid" +
                " where saleDate between ? and ?" +
                " and gender = ?" +
                " and sales.storeId = ?" +
                " group by season order by sum(quantitySold) desc limit 500";

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectBySeason);
        stmt.setDate(1, begin);
        stmt.setDate(2, end);
        stmt.setString(3, gender);
        stmt.setInt(4, storeId);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            ReportItem item = new ReportItem();
            item.setSeason(rs.getString("season"));
            item.setQuantitySold(rs.getInt("QuantitySold"));

            items.add(item);
        }

        return items;
    }

    public ArrayList<ReportItem> getItemsByCategory(Date begin, Date end, String gender, int storeId) throws SQLException {
        ArrayList<ReportItem> items = new ArrayList<ReportItem>();
        int stmtCount = 0;

        String selectByCategory = "SELECT category, sum(quantitySold) as QuantitySold" +
                " from saleitems sales inner join inventoryitems inv on sales.inventoryItemid = inv.inventoryitemid" +
                " where saleDate between ? and ?" +
                " and gender = ?" +
                " and sales.storeId = ?" +
                " group by category order by sum(quantitySold) desc limit 500";

        Connection con = dbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectByCategory);
        stmt.setDate(1, begin);
        stmt.setDate(2, end);
        stmt.setString(3, gender);
        stmt.setInt(4, storeId);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            ReportItem item = new ReportItem();
            item.setCategory(rs.getString("category"));
            item.setQuantitySold(rs.getInt("QuantitySold"));

            items.add(item);
        }

        return items;
    }
}
