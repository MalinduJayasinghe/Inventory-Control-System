module lk.ijse.inventory_control_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens lk.ijse.inventory_control_system.controllers to javafx.fxml;
    opens lk.ijse.inventory_control_system.dto to javafx.base;
    
    exports lk.ijse.inventory_control_system;
    exports lk.ijse.inventory_control_system.controllers;
    exports lk.ijse.inventory_control_system.dto;
}
