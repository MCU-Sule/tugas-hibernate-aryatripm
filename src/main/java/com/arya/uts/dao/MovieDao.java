package com.arya.uts.dao;

import com.arya.uts.model.Movie;
import com.arya.uts.model.User;
import com.arya.uts.utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDao implements DaoInterface<Movie>{

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Integer addData(Movie data) {
        String query = "INSERT INTO movie(Title, Genre, Durasi) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.getTitle());
            preparedStatement.setString(2, data.getGenre());
            preparedStatement.setInt(3, data.getDurasi());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ObservableList<Movie> getData() {
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        String query = "SELECT * FROM movie";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                movies.add(
                        new Movie(
                                result.getInt("idMovie"),
                                result.getString("Title"),
                                result.getString("Genre"),
                                result.getInt("Durasi")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public void updateData(Movie data) {
        String query = "UPDATE movie " +
                "SET Title = ?, Genre = ?, Durasi = ? " +
                "WHERE idMovie = ?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.getTitle());
            preparedStatement.setString(2, data.getGenre());
            preparedStatement.setInt(3, data.getDurasi());
            preparedStatement.setInt(4, data.getId());
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer delData(Movie data) {
        String query = "DELETE FROM movie WHERE idMovie = ?";
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

    public ObservableList<String> getGenres() {
        ObservableList<String> genres = FXCollections.observableArrayList();

        String query = "SELECT genre FROM movie";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String genre1 = result.getString("genre");
                String[] genre2 = genre1.split("[,]", 0);
                for (String g: genre2) {
                    if (!genres.contains(g.trim())) {
                        genres.add(g.trim());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }
}
