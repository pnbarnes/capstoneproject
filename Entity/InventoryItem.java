package Entity;

// this class stores data for an item used in both the sales and shipping systems
// the fields in this class will match the fields in the inventory database
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class InventoryItem {

    private int itemId;
    private String size;
    private String UPC;
    private String color;
    private int quantityOnHand;
    private int storeId;
    private String storeName;
    private String phoneNumber;
    private String zipCode;
    private String itemName;
    private String gender;
    private String category;
    private String season;

    // constructor
    public InventoryItem(){
        
    }

    // getters 

    public int getItemId() {
        return itemId;
    }

    public String getSize() {
        return size;
    }

    public String getUPC() {
        return UPC;
    }

    public String getColor() {
        return color;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    // setters

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUPC(String UPC){
        this.UPC = UPC;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setQuantityOnHand(int quantity){
        this.quantityOnHand = quantity;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}