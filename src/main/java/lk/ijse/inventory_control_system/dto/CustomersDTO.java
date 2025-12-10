package lk.ijse.inventory_control_system.dto;

public class CustomersDTO {
    
    private int customerId;
    private String customerName;
    private String address;
    private String contactNumber;

    public CustomersDTO() {
    }

    public CustomersDTO(int customerId, String customerName, String address, String contactNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}

