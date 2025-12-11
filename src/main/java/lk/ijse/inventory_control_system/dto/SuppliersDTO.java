package lk.ijse.inventory_control_system.dto;

public class SuppliersDTO {
    private int Supplier_ID;
    private String SupplierName;
    private String Address;
    private String Email;
    private String ContactNumber;

    public SuppliersDTO(int supplierID, String supplierName, String address, String email, String contactNumber) {
        this.Supplier_ID = supplierID;
        this.SupplierName = supplierName;
        this.Address = address;
        this.Email = email;
        this.ContactNumber = contactNumber;
    }

    public SuppliersDTO(String supplierName, String address, String email, String contactNumber) {
        this.SupplierName = supplierName;
        this.Address = address;
        this.Email = email;
        this.ContactNumber = contactNumber;
    }

    public int getSupplierID() { return Supplier_ID; }
    public void setSupplierID(int supplierID) { this.Supplier_ID = supplierID; }

    public String getSupplierName() { return SupplierName; }
    public void setSupplierName(String supplierName) { this.SupplierName = supplierName; }

    public String getAddress() { return Address; }
    public void setAddress(String address) { this.Address = address; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public String getContactNumber() { return ContactNumber; }
    public void setContactNumber(String contactNumber) { this.ContactNumber = contactNumber; }
}
