package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.DashboardDTO;
import lk.ijse.inventory_control_system.dto.LowStockItemDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardModel {
    
    public DashboardDTO getDashboardStatistics() throws SQLException {
        DashboardDTO dto = new DashboardDTO();
        
        ResultSet rsItems = CrudUtil.execute("SELECT COUNT(*) as total FROM Items");
        if (rsItems.next()) {
            dto.setTotalItems(rsItems.getInt("total"));
        }
        
        ResultSet rsLowStock = CrudUtil.execute("SELECT COUNT(*) as total FROM Items WHERE ItemQuantity < 50");
        if (rsLowStock.next()) {
            dto.setLowStockItems(rsLowStock.getInt("total"));
        }
        
        ResultSet rsPurchaseOrders = CrudUtil.execute("SELECT COUNT(*) as total FROM PurchaseOrder");
        if (rsPurchaseOrders.next()) {
            dto.setTotalOrders(rsPurchaseOrders.getInt("total"));
        }
        
        ResultSet rsDamaged = CrudUtil.execute("SELECT COUNT(*) as total FROM DamagedItems");
        if (rsDamaged.next()) {
            dto.setDamagedItems(rsDamaged.getInt("total"));
        }
        
        return dto;
    }
    
    public List<LowStockItemDTO> getLowStockItems() throws SQLException {
        List<LowStockItemDTO> list = new ArrayList<>();
        
        ResultSet rs = CrudUtil.execute(
            "SELECT i.ItemName, i.ItemQuantity, s.SupplierName " +
            "FROM Items i " +
            "JOIN Suppliers s ON i.Supplier_ID = s.Supplier_ID " +
            "WHERE i.ItemQuantity < 50 " +
            "ORDER BY i.ItemQuantity ASC"
        );
        
        while (rs.next()) {
            list.add(new LowStockItemDTO(
                rs.getString("ItemName"),
                rs.getInt("ItemQuantity"),
                rs.getString("SupplierName")
            ));
        }
        
        return list;
    }
}