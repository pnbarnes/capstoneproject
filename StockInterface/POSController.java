package StockInterface;

import Entity.InventoryItem;
import ISMS.ISMSContext;
import Entity.Finder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class POSController implements Initializable {

    InventoryHelper inventoryHelper = new InventoryHelper();
    Finder Finder = new Finder();
    ISMSContext context = new ISMSContext();

    public void setContext(ISMSContext context){
        this.context = context;
    }

    @FXML
    private TextField UPC;

    @FXML
    private TextField quantity;


    @FXML
    private Button saleButton;

    @FXML
    private Label message;

    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    public void POSUpdate(ActionEvent event) throws SQLException {
        String UPC = this.UPC.getText();
        InventoryItem item = this.Finder.itemFinder(UPC);

        if(item != null && !quantity.getText().isEmpty()){
            int soldQuantity = Integer.parseInt(quantity.getText());

            if(item.getQuantityOnHand() - soldQuantity >= 0){
                int newQuantity= item.getQuantityOnHand() - soldQuantity;
                item.setQuantityOnHand(newQuantity);

                this.inventoryHelper.updateInventory(item);
                this.inventoryHelper.insertSale(item, this.context.getCurrentStore(), soldQuantity);

                message.setText("Successfully updated inventory");
            }

            else{
                message.setText("Stock is too low for sale amount");
            }

        }

        else{
            message.setText("Invalid UPC");
        }
    }
}
