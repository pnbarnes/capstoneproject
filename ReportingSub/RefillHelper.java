package ReportingSub;

import Entity.Finder;
import Entity.InventoryItem;
import Entity.RefillItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Objects;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 05/21
 */
public class RefillHelper {

    private ObservableList<RefillItem> refillReport = FXCollections.observableArrayList();;

    public void addToRefillReport(int fullStack, String UPC, int numOnFloor) throws SQLException {
        RefillItem refillItem = new RefillItem();
        refillItem.setFullStack(fullStack);
        refillItem.setUPC(UPC);

        refillItem.setRefillQuantity(getRefillQuantity(numOnFloor, fullStack));
        refillItem.setRefillInStock(isRefillInStock(refillItem));

        if(refillItem.getRefillInStock()){
            // if refill item is in stock, adds size and color info
            Finder finder = new Finder();
            InventoryItem itemInStock = finder.itemFinder(refillItem.getUPC());
            if(!Objects.isNull(itemInStock)){
                refillItem.setItemName(itemInStock.getItemName());
                refillItem.setColor(itemInStock.getColor());
                refillItem.setSize(itemInStock.getSize());
            }
        }

        refillReport.add(refillItem);
    }

    public ObservableList<RefillItem> getRefillReport(){
        return refillReport;
    }


    public int getRefillQuantity(int numOnFloor, int fullStack){

        int refillQuantity = fullStack - numOnFloor;

        return refillQuantity;
    }

    public Boolean isRefillInStock(RefillItem item) throws SQLException {
        Boolean inStock = false;

        Finder finder = new Finder();

        InventoryItem foundItem = finder.itemFinder(item.getUPC());

        if(!Objects.isNull(foundItem) && foundItem.getQuantityOnHand() >= item.getRefillQuantity()){

            inStock = true;

        }

        return inStock;
    }

    public void resetRefillReport(){
        this.refillReport.clear();
    }
}
