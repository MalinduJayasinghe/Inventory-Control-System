package lk.ijse.inventory_control_system.dto;

public class CustomerOrderItemDTO {
    private int id;
    private int orderID;
    private int itemID;
    private int qty;
    private double unitPrice;

    public CustomerOrderItemDTO() {
    }

    public CustomerOrderItemDTO(int itemID, int qty, double unitPrice) {
        this.itemID = itemID;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public CustomerOrderItemDTO(int id, int itemID, int qty, double unitPrice) {
        this.id = id;
        this.itemID = itemID;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public int getOrderID() { 
        return orderID; 
    }

    public void setOrderID(int orderID) { 
        this.orderID = orderID; 
    }

    public int getItemID() { 
        return itemID; 
    }

    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public int getQty() { 
        return qty; 
    }

    public void setQty(int qty) { 
        this.qty = qty; 
    }

    public double getUnitPrice() { 
        return unitPrice; 
    }

    public void setUnitPrice(double unitPrice) { 
        this.unitPrice = unitPrice; 
    }

    @Override
    public String toString() {
        return "CustomerOrderItemDTO{" +
                "id=" + id +
                ", orderID=" + orderID +
                ", itemID=" + itemID +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}