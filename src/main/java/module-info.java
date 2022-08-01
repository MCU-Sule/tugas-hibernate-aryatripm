module com.arya.uts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires mysql.connector.java;


    opens com.arya.uts to javafx.fxml;
    exports com.arya.uts;
    exports com.arya.uts.controller;
    exports com.arya.uts.dao;
    exports com.arya.uts.model;
    exports com.arya.uts.utility;
}