package ISMS;

import Entity.Employee;
import Entity.Store;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class ISMSContext {
    // stores context for the entire ISMS application

    private Employee loggedInEmployee;
    private Store currentStore = new Store();

    public void setLoggedInEmployee(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public Employee getLoggedInEmployee(){
        return this.loggedInEmployee;
    }

    public void setCurrentStore(Store currentStore) {
        this.currentStore = currentStore;
    }

    public Store getCurrentStore() {
        return currentStore;
    }
}
