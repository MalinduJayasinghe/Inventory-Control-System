package lk.ijse.inventory_control_system.dto;

public class PurchaseOrderItemDTO {

    private int id;
    private int pOrderID;
    private int itemID;
    private int qty;
    private double unitPrice;

    public PurchaseOrderItemDTO() {
    }

    public PurchaseOrderItemDTO(int itemID, int qty, double unitPrice) {
        this.itemID = itemID;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public PurchaseOrderItemDTO(int id, int itemID, int qty, double unitPrice) {
        this.id = id;
        this.itemID = itemID;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getpOrderID() { return pOrderID; }
    public void setpOrderID(int pOrderID) { this.pOrderID = pOrderID; }

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return "PurchaseOrderItemDTO{" +
                "id=" + id +
                ", pOrderID=" + pOrderID +
                ", itemID=" + itemID +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
