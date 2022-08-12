package com.arya.uts.controller;

import com.arya.uts.Application;
import com.arya.uts.dao.MovieDao;
import com.arya.uts.dao.UserDao;
import com.arya.uts.dao.WatchlistDao;
import com.arya.uts.model.*;
import com.arya.uts.utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class FirstPageController {

    public ComboBox<String> cmbGenre;
    public ListView<UserEntity> lvUser;
    public TableView<MovieEntity> table1;
    public TableView<WatchListEntity> table2;
    public TableColumn<MovieEntity, String> MovieTitleColumn;
    public TableColumn<MovieEntity, String> MovieGenreColumn;
    public TableColumn<MovieEntity, Integer> MovieDurasiColumn;
    public TableColumn<WatchListEntity, String> WatchlistTitleColumn;
    public TableColumn<WatchListEntity, Integer> WatchlistLastwatchColumn;
    public TableColumn<WatchListEntity, Boolean> WatchlistFavoriteColumn;

    private ObservableList<String> genres;
    private ObservableList<UserEntity> users;
    private ObservableList<MovieEntity> movies;
    private ObservableList<WatchListEntity> watchlist;

    private UserDao userDao;
    private MovieDao movieDao;
    private WatchlistDao watchlistDao;

    public void initialize() {
        userDao = new UserDao();
        movieDao = new MovieDao();
        watchlistDao = new WatchlistDao();

        genres = FXCollections.observableArrayList("All");
        genres.addAll(movieDao.getGenres());
        users = userDao.getData();
        movies = movieDao.getData();
        watchlist = watchlistDao.getData();

        cmbGenre.setItems(genres);
        cmbGenre.getSelectionModel().selectFirst();
        lvUser.setItems(users);
        table1.setItems(movies);
        table2.setItems(watchlist);

        MovieTitleColumn.setCellValueFactory(new PropertyValueFactory<MovieEntity, String>("title"));
        MovieGenreColumn.setCellValueFactory(new PropertyValueFactory<MovieEntity, String>("genre"));
        MovieDurasiColumn.setCellValueFactory(new PropertyValueFactory<MovieEntity, Integer>("durasi"));

        WatchlistTitleColumn.setCellValueFactory(new PropertyValueFactory<WatchListEntity, String>("movieByMovieIdMovie"));
        WatchlistLastwatchColumn.setCellValueFactory(new PropertyValueFactory<WatchListEntity, Integer>("lastWatchPerDurasi"));
        WatchlistFavoriteColumn.setCellValueFactory(new PropertyValueFactory<WatchListEntity, Boolean>("favorite"));
    }

    public void changeCombo(ActionEvent actionEvent) {
        movies = movieDao.getData();
        if (!cmbGenre.getSelectionModel().getSelectedItem().equals("All")) {
            movies = movies.filtered(movie -> movie.getGenre().contains(cmbGenre.getSelectionModel().getSelectedItem()));
        }
        table1.setItems(movies);
    }

    public void AddUserAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("UTSSEcondPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        SecondPageController secondPageController = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle("Tambah User");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        if (secondPageController.isSubmit) {
            int result = userDao.addData(new UserEntity(
                    secondPageController.txtUserName.getText(),
                    secondPageController.txtPassword.getText()
            ));
            if (result > 0) {
                showAlert("User ditambahkan");
            }
        }

        users = userDao.getData();
        lvUser.setItems(users);
    }

    public void DelUserAction(ActionEvent actionEvent) {
        if (!lvUser.getSelectionModel().getSelectedItems().isEmpty()) {
            int result = userDao.delData(lvUser.getSelectionModel().getSelectedItem());
            if (result > 0) {
                showAlert("User deleted");
            }
        }
        users = userDao.getData();
        lvUser.setItems(users);
    }

    public void printReport(ActionEvent actionEvent) throws JRException {
        Connection connection = DatabaseConnection.getConnection();
        Map parameter = new HashMap();
        JasperPrint jasperPrint = JasperFillManager.fillReport("reports/MovieReport.jasper", parameter, connection);
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setVisible(true);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }


    public void userClicked(MouseEvent mouseEvent) {
        if (!lvUser.getSelectionModel().getSelectedItems().isEmpty()) {
            watchlist = watchlistDao.getData();

            UserEntity user = lvUser.getSelectionModel().getSelectedItem();

            watchlist = watchlist.filtered(watchlist1 -> watchlist1.getUserByUserIdUser().getIdUser().equals(user.getIdUser()));

            table2.setItems(watchlist);
        }
    }
}
