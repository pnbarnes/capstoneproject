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
public class SARAController implements Initializable {

    InventoryHelper inventoryHelper = new InventoryHelper();
    Finder Finder = new Finder();
    ISMSContext context = new ISMSContext();
    StockWaitlistInterface waitlistInterface = new StockWaitlistInterface();

    public void setContext(ISMSContext context){
        this.context = context;
    }

    @FXML
    private TextField UPC;

    @FXML
    private TextField quantity;

    @FXML
    private Button stockButton;

    @FXML
    private Label message;

    public void initialize(URL url, ResourceBundle rb){ }

    @FXML
    public void SARAUpdate(ActionEvent event) throws SQLException {
        String UPC = this.UPC.getText();
        int quantity = Integer.parseInt(this.quantity.getText());
        int storeId = this.context.getCurrentStore().getStoreID();

        InventoryItem item = new InventoryItem();

        if(this.inventoryHelper.isNewItem(UPC)){
            item.setUPC(UPC);
            item.setQuantityOnHand(quantity);
            item.setStoreId(storeId);
            this.inventoryHelper.addToInventory(item);

            waitlistInterface.triggerWaitlistCheck(item);
        }
        else{
            item = this.Finder.itemFinder(UPC);
            if(item != null){
                int newQuantity= item.getQuantityOnHand() + quantity;
                item.setQuantityOnHand(newQuantity);

                this.inventoryHelper.updateInventory(item);
                String success = "Successfully updated inventory ";
                String notif = waitlistInterface.triggerWaitlistCheck(item);
                if(notif != null){
                    message.setText(success + notif);
                }
                else{
                    message.setText(success);
                }


            }

            else{
                message.setText("Invalid UPC");
            }
        }




    }


}
