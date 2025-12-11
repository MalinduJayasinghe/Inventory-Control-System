package lk.ijse.inventory_control_system.dto;

public class ItemsDTO {
    private int Item_ID;
    private String ItemName;
    private int ItemQuantity;
    private double UnitPrice;
    private String Category;
    private int Supplier_ID;

    public ItemsDTO() {
    }

    public ItemsDTO(int itemID, String itemName, int itemQuantity, double unitPrice, String category, int supplierID) {
        this.Item_ID = itemID;
        this.ItemName = itemName;
        this.ItemQuantity = itemQuantity;
        this.UnitPrice = unitPrice;
        this.Category = category;
        this.Supplier_ID = supplierID;
    }

    public ItemsDTO(String itemName, int itemQuantity, double unitPrice, String category, int supplierID) {
        this.ItemName = itemName;
        this.ItemQuantity = itemQuantity;
        this.UnitPrice = unitPrice;
        this.Category = category;
        this.Supplier_ID = supplierID;
    }

    public int getItemID() { return Item_ID; }
    public void setItemID(int itemID) { this.Item_ID = itemID; }

    public String getItemName() { return ItemName; }
    public void setItemName(String itemName) { this.ItemName = itemName; }

    public int getItemQuantity() { return ItemQuantity; }
    public void setItemQuantity(int itemQuantity) { this.ItemQuantity = itemQuantity; }

    public double getUnitPrice() { return UnitPrice; }
    public void setUnitPrice(double unitPrice) { this.UnitPrice = unitPrice; }

    public String getCategory() { return Category; }
    public void setCategory(String category) { this.Category = category; }

    public int getSupplierID() { return Supplier_ID; }
    public void setSupplierID(int supplierID) { this.Supplier_ID = supplierID; }
}
