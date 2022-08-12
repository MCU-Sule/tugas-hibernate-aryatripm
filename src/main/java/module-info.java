module com.arya.uts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires mysql.connector.java;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.arya.uts to javafx.fxml;
    opens com.arya.uts.model;
    exports com.arya.uts;
    exports com.arya.uts.controller;
    exports com.arya.uts.dao;
    exports com.arya.uts.model;
    exports com.arya.uts.utility;
}