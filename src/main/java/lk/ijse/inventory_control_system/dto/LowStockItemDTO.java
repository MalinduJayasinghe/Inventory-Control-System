package lk.ijse.inventory_control_system.dto;

public class LowStockItemDTO {
    private int itemID;
    private String itemName;
    private int itemQuantity;
    private String category;
    private String supplierName;

    public LowStockItemDTO(String itemName, int itemQuantity, String supplierName) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.supplierName = supplierName;
    }

    public LowStockItemDTO() {
    }

    public LowStockItemDTO(int itemID, String itemName, int itemQuantity, String category, String supplierName) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.category = category;
        this.supplierName = supplierName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return "LowStockItemDTO{" +
                "itemID=" + itemID +
                ", itemName='" + itemName + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", category='" + category + '\'' +
                ", supplierName='" + supplierName + '\'' +
                '}';
    }
}