package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class PurchaseOrderViewDTO {

    private int pOrderID;
    private String supplierName;
    private Date orderDate;
    private double totalAmount;

    public PurchaseOrderViewDTO(int pOrderID, String supplierName, Date orderDate, double totalAmount) {
        this.pOrderID = pOrderID;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getpOrderID() {
        return pOrderID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "PurchaseOrderViewDTO{" +
                "pOrderID=" + pOrderID +
                ", supplierName='" + supplierName + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
