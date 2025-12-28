package lk.ijse.inventory_control_system.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.inventory_control_system.db.DBConnection;
import lk.ijse.inventory_control_system.dto.PurchaseOrderDTO;

public class PurchaseOrderModel {

    public int saveAndGetID(PurchaseOrderDTO po) throws SQLException {
        String sql = "INSERT INTO PurchaseOrder (Supplier_ID, OrderDate) VALUES (?, ?)";
        
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setObject(1, po.getSupplierID());
        pst.setObject(2, po.getOrderDate());

        int affectedRows = pst.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to insert PurchaseOrder, no rows affected.");
        }

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Failed to insert PurchaseOrder, no ID obtained.");
        }
    }    
}

