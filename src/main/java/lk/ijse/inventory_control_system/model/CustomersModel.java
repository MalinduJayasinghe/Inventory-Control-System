package lk.ijse.inventory_control_system.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventory_control_system.dto.CustomersDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

public class CustomersModel {
    
    public boolean saveCustomer(CustomersDTO customerDTO) throws SQLException {
        
        boolean result = CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?)");
        return result;
    }
    
    public CustomersDTO searchCustomer(String customerId) throws SQLException {
    
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer WHERE Customer_ID =?", customerId);
        
        CustomersDTO customerDTO = null;
        
        if(rs.next()) {
            int cusId = rs.getInt("Customer_ID");
            String cusName = rs.getString("CustomerName ");
            String cusAddress = rs.getString("Address ");
            String cusNumber = rs.getString("ContactNumber ");
            
            customerDTO = new CustomersDTO(cusId, cusName, cusAddress, cusNumber);
        }
        
        return customerDTO;     
    }
    
    public boolean updateCustomer(CustomersDTO dto) throws SQLException {

        boolean result = CrudUtil.execute("UPDATE Customer SET CustomerName=?, Address=?, ContactNumber=? WHERE Customer_ID=?");
        return result;
    }
    
    public boolean deleteCustomer(String customerId) throws SQLException {

        boolean result = CrudUtil.execute("DELETE FROM Customer WHERE Customer_ID=?", customerId);
        return result;
    }
    
    public List<CustomersDTO> getAllCustomers() throws SQLException {
    
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer");
        
        List<CustomersDTO> customerList = new ArrayList<>();
        
        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("CustomerName");
            String address = rs.getString("Address");
            String contactNumber = rs.getString("ContactNumber");
            
            CustomersDTO customerDTO = new CustomersDTO(customerId, customerName, address, contactNumber);
            customerList.add(customerDTO);
        }        
        return customerList;
    }
}
