package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.SuppliersDTO;
import lk.ijse.inventory_control_system.dto.SupplierComboDTO;
import lk.ijse.inventory_control_system.dto.SuppliersViewDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersModel {

    public boolean saveSupplier(SuppliersDTO supplier) throws SQLException {
        return CrudUtil.execute(
            "INSERT INTO Suppliers (SupplierName, Address, Email, ContactNumber) VALUES (?,?,?,?)",
            supplier.getSupplierName(),
            supplier.getAddress(),
            supplier.getEmail(),
            supplier.getContactNumber()
        );
    }

    public SuppliersDTO searchSupplier(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Suppliers WHERE Supplier_ID=?", Integer.parseInt(id));
        if (rs.next()) {
            return new SuppliersDTO(
                rs.getInt("Supplier_ID"),
                rs.getString("SupplierName"),
                rs.getString("Address"),
                rs.getString("Email"),
                rs.getString("ContactNumber")
            );
        }
        return null;
    }

    public boolean updateSupplier(SuppliersDTO supplier) throws SQLException {
        return CrudUtil.execute(
            "UPDATE Suppliers SET SupplierName=?, Address=?, Email=?, ContactNumber=? WHERE Supplier_ID=?",
            supplier.getSupplierName(),
            supplier.getAddress(),
            supplier.getEmail(),
            supplier.getContactNumber(),
            supplier.getSupplierID()
        );
    }

    public boolean deleteSupplier(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Suppliers WHERE Supplier_ID=?", Integer.parseInt(id));
    }

    public List<SuppliersViewDTO> getAllSuppliersForView() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Suppliers");
        List<SuppliersViewDTO> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new SuppliersViewDTO(
                rs.getInt("Supplier_ID"),
                rs.getString("SupplierName"),
                rs.getString("Address"),
                rs.getString("Email"),
                rs.getString("ContactNumber")
            ));
        }
        return list;
    }

    public List<SupplierComboDTO> getSuppliersForCombo() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT Supplier_ID, SupplierName FROM Suppliers");
        List<SupplierComboDTO> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new SupplierComboDTO(
                rs.getInt("Supplier_ID"),
                rs.getString("SupplierName")
            ));
        }
        return list;
    }
}
