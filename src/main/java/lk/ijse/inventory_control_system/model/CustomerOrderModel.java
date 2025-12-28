package lk.ijse.inventory_control_system.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.inventory_control_system.db.DBConnection;
import lk.ijse.inventory_control_system.dto.CustomerOrderDTO;

public class CustomerOrderModel {
    public int saveAndGetID(CustomerOrderDTO co) throws SQLException {
        String sql = "INSERT INTO CustomerOrder (Customer_ID, Date) VALUES (?, ?)";
        
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setObject(1, co.getCustomerID());
        pst.setObject(2, co.getOrderDate());
        int affectedRows = pst.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to insert CustomerOrder, no rows affected.");
        }
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Failed to insert CustomerOrder, no ID obtained.");
        }
    }
}