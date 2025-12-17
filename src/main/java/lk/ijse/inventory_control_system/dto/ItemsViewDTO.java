package lk.ijse.inventory_control_system.dto;

public class ItemsViewDTO {
    private int itemID;
    private String itemName;
    private int itemQuantity;
    private double unitPrice;
    private String category;
    private int supplierID;

    public ItemsViewDTO(int itemID, String itemName, int itemQuantity, double unitPrice, String category, int supplierID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplierID = supplierID;
    }

    public int getItemID() { return itemID; }
    public String getItemName() { return itemName; }
    public int getItemQuantity() { return itemQuantity; }
    public double getUnitPrice() { return unitPrice; }
    public String getCategory() { return category; }
    public int getSupplierID() { return supplierID; }
}
