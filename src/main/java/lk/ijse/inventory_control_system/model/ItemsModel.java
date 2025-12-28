package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.ItemsDTO;
import lk.ijse.inventory_control_system.dto.ItemComboDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventory_control_system.dto.ItemsViewDTO;

public class ItemsModel {

    public boolean saveItem(ItemsDTO item) throws SQLException {
        return CrudUtil.execute(
            "INSERT INTO Items (ItemName, ItemQuantity, UnitPrice, Category, Supplier_ID) VALUES (?,?,?,?,?)",
            item.getItemName(), item.getItemQuantity(), item.getUnitPrice(), item.getCategory(), item.getSupplierID()
        );
    }

    public boolean updateItem(ItemsDTO item) throws SQLException {
        return CrudUtil.execute(
            "UPDATE Items SET ItemName=?, ItemQuantity=?, UnitPrice=?, Category=?, Supplier_ID=? WHERE Item_ID=?",
            item.getItemName(), item.getItemQuantity(), item.getUnitPrice(), item.getCategory(), item.getSupplierID(), item.getItemID()
        );
    }

    public boolean deleteItem(int itemID) throws SQLException {
        return CrudUtil.execute("DELETE FROM Items WHERE Item_ID=?", itemID);
    }

    public ItemsDTO searchItem(int itemID) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Items WHERE Item_ID=?", itemID);
        if(rs.next()){
            return new ItemsDTO(
                rs.getInt("Item_ID"),
                rs.getString("ItemName"),
                rs.getInt("ItemQuantity"),
                rs.getDouble("UnitPrice"),
                rs.getString("Category"),
                rs.getInt("Supplier_ID")
            );
        }
        return null;
    }

    public List<ItemsDTO> getAllItems() throws SQLException {
        List<ItemsDTO> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM Items");
        while(rs.next()){
            list.add(new ItemsDTO(
                rs.getInt("Item_ID"),
                rs.getString("ItemName"),
                rs.getInt("ItemQuantity"),
                rs.getDouble("UnitPrice"),
                rs.getString("Category"),
                rs.getInt("Supplier_ID")
            ));
        }
        return list;
    }
    
    public List<ItemsViewDTO> getAllItemsForView() throws SQLException {
        List<ItemsViewDTO> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
            "SELECT i.Item_ID, i.ItemName, i.ItemQuantity, i.UnitPrice, i.Category, s.SupplierName " +
            "FROM Items i " +
            "JOIN Suppliers s ON i.Supplier_ID = s.Supplier_ID"
        );

        while (rs.next()) {
            list.add(new ItemsViewDTO(
                rs.getInt("Item_ID"),
                rs.getString("ItemName"),
                rs.getInt("ItemQuantity"),
                rs.getDouble("UnitPrice"),
                rs.getString("Category"),
                rs.getString("SupplierName")
            ));
        }
        return list;
    }

    public List<ItemComboDTO> getItemsForCombo() throws SQLException {
        List<ItemComboDTO> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT Item_ID, ItemName FROM Items");
        while(rs.next()){
            list.add(new ItemComboDTO(rs.getInt("Item_ID"), rs.getString("ItemName")));
        }
        return list;
    }
    
    public String getItemNameByID(int itemID) throws SQLException {
    ResultSet rs = CrudUtil.execute("SELECT ItemName FROM Items WHERE Item_ID=?", itemID);
    if (rs.next()) {
        return rs.getString("ItemName");
    }
    return "Unknown";
}

    public double getItemPriceByID(int itemID) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT UnitPrice FROM Items WHERE Item_ID=?", itemID);
        if(rs.next()) {
            return rs.getDouble("UnitPrice");
        }
        return 0.0;
    }
    
    public boolean increaseItemQty(int itemID, int qty) throws SQLException {
        if (qty <= 0) return false;
        return CrudUtil.execute("UPDATE Items SET ItemQuantity = ItemQuantity + ? WHERE Item_ID = ?", qty, itemID);
    }
    
    public boolean decreaseItemQty(int itemID, int qty) throws SQLException {
        return CrudUtil.execute("UPDATE Items SET ItemQuantity = ItemQuantity - ? WHERE Item_ID = ? AND ItemQuantity >= ?", qty, itemID, qty);
    }
}
