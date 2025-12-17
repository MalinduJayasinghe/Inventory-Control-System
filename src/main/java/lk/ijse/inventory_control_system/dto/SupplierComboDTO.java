package lk.ijse.inventory_control_system.dto;

public class SupplierComboDTO {
    private int supplierID;
    private String supplierName;

    public SupplierComboDTO(int supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    @Override
    public String toString() {
        return supplierID + " - " + supplierName;
    }
}
