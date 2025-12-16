package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventory_control_system.dto.CustomersDTO;

public class CustomersModel {

    // Save a new customer (ID is auto-generated)
    public boolean saveCustomer(CustomersDTO customer) throws SQLException {
        return CrudUtil.execute(
            "INSERT INTO Customer (CustomerName, Address, ContactNumber) VALUES (?,?,?)",
            customer.getCustomerName(),
            customer.getAddress(),
            customer.getContactNumber()
        );
    }

    public CustomersDTO searchCustomer(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT * FROM Customer WHERE Customer_ID=?",
            Integer.parseInt(id)
        );
        if (rs.next()) {
            return new CustomersDTO(
                rs.getInt("Customer_ID"),
                rs.getString("CustomerName"),
                rs.getString("Address"),
                rs.getString("ContactNumber")
            );
        }
        return null;
    }

    public boolean updateCustomer(CustomersDTO customer) throws SQLException {
        return CrudUtil.execute(
            "UPDATE Customer SET CustomerName=?, Address=?, ContactNumber=? WHERE Customer_ID=?",
            customer.getCustomerName(),
            customer.getAddress(),
            customer.getContactNumber(),
            customer.getCustomerID()
        );
    }

    public boolean deleteCustomer(String id) throws SQLException {
        return CrudUtil.execute(
            "DELETE FROM Customer WHERE Customer_ID=?",
            Integer.parseInt(id)
        );
    }

    public List<CustomersDTO> getAllCustomers() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer");
        List<CustomersDTO> customerList = new ArrayList<>();

        while (rs.next()) {
            customerList.add(new CustomersDTO(
                rs.getInt("Customer_ID"),
                rs.getString("CustomerName"),
                rs.getString("Address"),
                rs.getString("ContactNumber")
            ));
        }

        return customerList;
    }
}
