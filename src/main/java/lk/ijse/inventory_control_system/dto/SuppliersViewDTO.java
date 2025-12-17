package lk.ijse.inventory_control_system.dto;

public class SuppliersViewDTO {

    private int supplierID;
    private String supplierName;
    private String address;
    private String email;
    private String contactNumber;

    public SuppliersViewDTO(int supplierID, String supplierName, String address, String email, String contactNumber) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.address = address;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public int getSupplierID() { return supplierID; }
    public String getSupplierName() { return supplierName; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getContactNumber() { return contactNumber; }
}
