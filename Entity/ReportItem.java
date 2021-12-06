package Entity;

public class ReportItem extends InventoryItem {

    private int quantitySold;

    public ReportItem(){};

    public int getQuantitySold() {
        return quantitySold;
    }


    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    @Override
    public String toString(){
        String itemId = Integer.toString(this.getItemId());
        String storeId = Integer.toString(this.getStoreId());
        String UPC = this.getUPC();
        String itemName = this.getItemName();
        String size = this.getSize();
        String color = this.getColor();
        String quantityAvail = Integer.toString(this.getQuantityOnHand());
        String quantitySold = Integer.toString(this.getQuantitySold());
        String delimiter = ",";
        return itemId + delimiter + storeId + delimiter + UPC + delimiter + itemName + delimiter + size + delimiter
                + color + delimiter + quantityAvail + delimiter + quantitySold;
    }
}
