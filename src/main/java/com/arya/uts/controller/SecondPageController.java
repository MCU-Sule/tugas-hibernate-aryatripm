package com.arya.uts.controller;

import com.arya.uts.dao.UserDao;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SecondPageController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Boolean isSubmit;

    public void initialize() {
        isSubmit = false;
    }

    public void submit(ActionEvent actionEvent) {
        isSubmit = true;
        txtPassword.getScene().getWindow().hide();
    }
}
