package lk.ijse.inventory_control_system.dto;

public class CustomerOrderItemTM {
    private int itemID;
    private String itemName;
    private double unitPrice;
    private int qty;
    private double totalPrice;

    public CustomerOrderItemTM() {
    }

    public CustomerOrderItemTM(int itemID, String itemName, double unitPrice, int qty, double totalPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalPrice = totalPrice;
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

    public double getUnitPrice() { 
        return unitPrice; 
    }

    public void setUnitPrice(double unitPrice) { 
        this.unitPrice = unitPrice; 
    }

    public int getQty() { 
        return qty; 
    }

    public void setQty(int qty) { 
        this.qty = qty; 
    }

    public double getTotalPrice() { 
        return totalPrice; 
    }

    public void setTotalPrice(double totalPrice) { 
        this.totalPrice = totalPrice; 
    }
    
    public void recalcTotal() {
        this.totalPrice = this.unitPrice * this.qty;
    }

    @Override
    public String toString() {
        return "CustomerOrderItemTM{" +
                "itemID=" + itemID +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", totalPrice=" + totalPrice +
                '}';
    }
}