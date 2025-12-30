package lk.ijse.inventory_control_system.dto;

public class CustomersViewDTO {
    private int customerID;
    private String customerName;
    private String address;
    private String contactNumber;

    public CustomersViewDTO(int customerID, String customerName, String address, String contactNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public int getCustomerID() { 
        return customerID; 
    }

    public String getCustomerName() { 
        return customerName; 
    }

    public String getAddress() { 
        return address; 
    }

    public String getContactNumber() { 
        return contactNumber; 
    }
}