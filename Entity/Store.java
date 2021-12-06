package Entity;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class Store {
    private int storeID;
    private String name;
    private String streetAddress;
    private Long zipCode;
    private String storeType;

    public Store(){}

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public int getStoreID() {
        return storeID;
    }
    public Long getZipCode() {
        return zipCode;
    }
    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    
}
