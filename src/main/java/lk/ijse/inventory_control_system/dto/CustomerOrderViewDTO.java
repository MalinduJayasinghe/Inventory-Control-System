package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class CustomerOrderViewDTO {
    private String customerName;
    private Date orderDate;
    private double totalAmount;

    public CustomerOrderViewDTO(int orderID, String customerName, Date orderDate, double totalAmount) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "CustomerOrderViewDTO{" +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}