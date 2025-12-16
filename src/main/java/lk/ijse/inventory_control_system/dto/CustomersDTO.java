package lk.ijse.inventory_control_system.dto;

public class CustomersDTO {
    private int Customer_ID;
    private String CustomerName;
    private String Address;
    private String ContactNumber;

    public CustomersDTO(int customerID, String customerName, String address, String contactNumber) {
        this.Customer_ID = customerID;
        this.CustomerName = customerName;
        this.Address = address;
        this.ContactNumber = contactNumber;
    }

    public CustomersDTO(String customerName, String address, String contactNumber) {
        this.CustomerName = customerName;
        this.Address = address;
        this.ContactNumber = contactNumber;
    }

    public int getCustomerID() { return Customer_ID; }
    public void setCustomerID(int customerID) { this.Customer_ID = customerID; }

    public String getCustomerName() { return CustomerName; }
    public void setCustomerName(String customerName) { this.CustomerName = customerName; }

    public String getAddress() { return Address; }
    public void setAddress(String address) { this.Address = address; }

    public String getContactNumber() { return ContactNumber; }
    public void setContactNumber(String contactNumber) { this.ContactNumber = contactNumber; }
}
