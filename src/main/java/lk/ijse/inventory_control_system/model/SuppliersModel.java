package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.SuppliersDTO;
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

    public List<SuppliersDTO> getAllSuppliers() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Suppliers");
        List<SuppliersDTO> supplierList = new ArrayList<>();

        while (rs.next()) {
            supplierList.add(new SuppliersDTO(
                rs.getInt("Supplier_ID"),
                rs.getString("SupplierName"),
                rs.getString("Address"),
                rs.getString("Email"),
                rs.getString("ContactNumber")
            ));
        }

        return supplierList;
    }
}
