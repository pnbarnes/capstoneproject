package ISMS;

import Entity.Employee;
import Entity.InventoryItem;
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
public class ISMSHelper {

    private Connection con = null;

    // constructor
    public ISMSHelper(){
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





}
