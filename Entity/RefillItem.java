package Entity;

public class RefillItem {

    private String itemName;
    private String UPC;
    private String color;
    private String size;
    private int refillQuantity;
    private Boolean refillInStock;
    private int fullStack;

    // constructor
    public RefillItem(){

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRefillQuantity() {
        return refillQuantity;
    }

    public void setRefillQuantity(int refillQuantity) {
        this.refillQuantity = refillQuantity;
    }

    public Boolean getRefillInStock() {
        return refillInStock;
    }

    public void setRefillInStock(Boolean refillInStock) {
        this.refillInStock = refillInStock;
    }

    public int getFullStack() {
        return fullStack;
    }

    public void setFullStack(int fullStack) {
        this.fullStack = fullStack;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString(){
        String UPC = this.getUPC();
        String itemName = this.getItemName();
        String size = this.getSize();
        String color = this.getColor();
        String refillQuantity = Integer.toString(this.getRefillQuantity());
        String refillInStock = this.getRefillInStock().toString();
        String fullStack = Integer.toString(this.getFullStack());
        String delimiter = ",";
        return UPC + delimiter + itemName + delimiter + color + delimiter
                + size + delimiter + refillQuantity + delimiter + refillInStock + delimiter + fullStack;
    }
}
