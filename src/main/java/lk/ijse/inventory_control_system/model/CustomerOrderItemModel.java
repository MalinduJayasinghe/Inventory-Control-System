package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.CustomerOrderItemDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import lk.ijse.inventory_control_system.db.DBConnection;

public class CustomerOrderItemModel {
    private final ItemsModel itemsModel = new ItemsModel();

    public boolean saveCustomerOrderItems(int cOrderID, List<CustomerOrderItemDTO> itemList) throws SQLException {
        if (itemList == null || itemList.isEmpty()) {
            throw new SQLException("No customer order items to save.");
        }

        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            for (CustomerOrderItemDTO item : itemList) {
                boolean result = CrudUtil.execute(
                    "INSERT INTO CustomerOrderItem (c_order_id, item_id, qty, unit_price) VALUES (?, ?, ?, ?)",
                    cOrderID,              // <-- MUST pass the order ID
                    item.getItemID(),
                    item.getQty(),
                    item.getUnitPrice()
                );
                if (!result) {
                    throw new SQLException("Failed to insert customer order item: " + item.getItemID());
                }

                boolean stockUpdated = itemsModel.decreaseItemQty(item.getItemID(), item.getQty());
                if (!stockUpdated) {
                    throw new SQLException("Failed to update stock for item: " + item.getItemID());
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}