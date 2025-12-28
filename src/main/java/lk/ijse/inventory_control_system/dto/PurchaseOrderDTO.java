package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class PurchaseOrderDTO {

    private int pOrderID;
    private int supplierID;
    private Date OrderDate;

    public PurchaseOrderDTO() {
    }

    public PurchaseOrderDTO(int supplierID, Date orderDate) {
        this.supplierID = supplierID;
        this.OrderDate = orderDate;
    }

    public PurchaseOrderDTO(int pOrderID, int supplierID, Date orderDate) {
        this.pOrderID = pOrderID;
        this.supplierID = supplierID;
        this.OrderDate = orderDate;
    }

    public int getpOrderID() {
        return pOrderID;
    }

    public void setpOrderID(int pOrderID) {
        this.pOrderID = pOrderID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.OrderDate = orderDate;
    }

    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
                "pOrderID=" + pOrderID +
                ", supplierID=" + supplierID +
                ", orderDate=" + OrderDate +
                '}';
    }
}
