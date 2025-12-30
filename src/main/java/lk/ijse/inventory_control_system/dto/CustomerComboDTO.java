package lk.ijse.inventory_control_system.dto;

public class CustomerComboDTO {
    private int customerID;
    private String customerName;

    public CustomerComboDTO(int customerID, String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return customerID + " - " + customerName;
    }
}