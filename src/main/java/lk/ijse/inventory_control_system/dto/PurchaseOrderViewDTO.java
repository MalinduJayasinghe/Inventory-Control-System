package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class PurchaseOrderViewDTO {

    private String supplierName;
    private Date orderDate;
    private double totalAmount;

    public PurchaseOrderViewDTO(int pOrderID, String supplierName, Date orderDate, double totalAmount) {
        this.supplierName = supplierName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
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
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "PurchaseOrderViewDTO{" +
                ", supplierName='" + supplierName + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
