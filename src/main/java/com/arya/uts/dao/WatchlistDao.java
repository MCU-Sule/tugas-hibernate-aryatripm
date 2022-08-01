package com.arya.uts.dao;

import com.arya.uts.model.Movie;
import com.arya.uts.model.User;
import com.arya.uts.model.Watchlist;
import com.arya.uts.utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WatchlistDao implements DaoInterface<Watchlist> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Integer addData(Watchlist data) {
        String query = "INSERT INTO watchlist(LastWatch, Favorite, Movie_idMovie, User_idUser) VALUES(?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, data.getLastWatch());
            preparedStatement.setBoolean(2, data.getFavorite());
            preparedStatement.setInt(3, data.getMovie().getId());
            preparedStatement.setInt(4, data.getUser().getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ObservableList<Watchlist> getData() {
        ObservableList<Watchlist> watchLists = FXCollections.observableArrayList();

        String query = "SELECT * FROM watchlist " +
                "JOIN movie ON Movie_idMovie = idMovie " +
                "JOIN user ON User_idUser = idUser";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                watchLists.add(
                        new Watchlist(
                                result.getInt("idWatchList"),
                                result.getInt("LastWatch"),
                                result.getBoolean("Favorite"),
                                new Movie(
                                        result.getInt("idMovie"),
                                        result.getString("Title"),
                                        result.getString("Genre"),
                                        result.getInt("Durasi")
                                ),
                                new User(
                                        result.getInt("idUser"),
                                        result.getString("UserName"),
                                        result.getString("UserPassword")
                                )
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return watchLists;
    }

    @Override
    public void updateData(Watchlist data) {
        String query = "UPDATE watchlist " +
                "SET LastWatch = ?, Favorite = ?, Movie_idMovie = ?, User_idUser = ? " +
                "WHERE idLastWatch = ?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, data.getLastWatch());
            preparedStatement.setBoolean(2, data.getFavorite());
            preparedStatement.setInt(3, data.getMovie().getId());
            preparedStatement.setInt(4, data.getUser().getId());
            preparedStatement.setInt(5, data.getId());
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer delData(Watchlist data) {
        String query = "DELETE FROM watchlist WHERE idWatchList = ?";
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
