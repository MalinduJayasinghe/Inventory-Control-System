package lk.ijse.inventory_control_system.dto;

public class CustomersDTO {
    private int customerID;
    private String customerName;
    private String address;
    private String contactNumber;

    public CustomersDTO(int customerID, String customerName, String address, String contactNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public CustomersDTO(String customerName, String address, String contactNumber) {
        this.customerName = customerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public int getCustomerID() { 
        return customerID; 
    }

    public void setCustomerID(int customerID) { 
        this.customerID = customerID; 
    }

    public String getCustomerName() { 
        return customerName; 
    }

    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    }

    public String getAddress() { 
        return address; 
    }

    public void setAddress(String address) { 
        this.address = address; 
    }

    public String getContactNumber() { 
        return contactNumber; 
    }

    public void setContactNumber(String contactNumber) { 
        this.contactNumber = contactNumber; 
    }
}