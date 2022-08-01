package com.arya.uts.dao;

import com.arya.uts.model.User;
import com.arya.uts.utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements DaoInterface<User> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Integer addData(User data) {
        String query = "INSERT INTO user(UserName, UserPassword) VALUES(?, ?)";
        PreparedStatement preparedStatement;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.getUsername());
            preparedStatement.setString(2, data.getPassword());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ObservableList<User> getData() {
        ObservableList<User> users = FXCollections.observableArrayList();

        String query = "SELECT * FROM user";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                users.add(
                        new User(
                                result.getInt("idUser"),
                                result.getString("UserName"),
                                result.getString("UserPassword")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void updateData(User data) {
        String query = "UPDATE user " +
                "SET UserName = ?, UserPassword = ? " +
                "WHERE idUser = ?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.getUsername());
            preparedStatement.setString(2, data.getPassword());
            preparedStatement.setInt(3, data.getId());
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer delData(User data) {
        String query = "DELETE FROM user WHERE idUser = ?";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, data.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
