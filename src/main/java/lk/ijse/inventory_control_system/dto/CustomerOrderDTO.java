package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class CustomerOrderDTO {
    private int orderID;
    private int customerID;
    private Date orderDate;

    public CustomerOrderDTO() {
    }

    public CustomerOrderDTO(int customerID, Date orderDate) {
        this.customerID = customerID;
        this.orderDate = orderDate;
    }

    public CustomerOrderDTO(int orderID, int customerID, Date orderDate) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "CustomerOrderDTO{" +
                "orderID=" + orderID +
                ", customerID=" + customerID +
                ", orderDate=" + orderDate +
                '}';
    }
}