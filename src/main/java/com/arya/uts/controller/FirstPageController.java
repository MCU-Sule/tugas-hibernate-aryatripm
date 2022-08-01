package com.arya.uts.controller;

import com.arya.uts.Application;
import com.arya.uts.dao.MovieDao;
import com.arya.uts.dao.UserDao;
import com.arya.uts.dao.WatchlistDao;
import com.arya.uts.model.Movie;
import com.arya.uts.model.User;
import com.arya.uts.model.Watchlist;
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
    public ListView<User> lvUser;
    public TableView<Movie> table1;
    public TableView<Watchlist> table2;
    public TableColumn<Movie, String> MovieTitleColumn;
    public TableColumn<Movie, String> MovieGenreColumn;
    public TableColumn<Movie, Integer> MovieDurasiColumn;
    public TableColumn<Watchlist, String> WatchlistTitleColumn;
    public TableColumn<Watchlist, Integer> WatchlistLastwatchColumn;
    public TableColumn<Watchlist, Boolean> WatchlistFavoriteColumn;

    private ObservableList<String> genres;
    private ObservableList<User> users;
    private ObservableList<Movie> movies;
    private ObservableList<Watchlist> watchlist;

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

        MovieTitleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        MovieGenreColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));
        MovieDurasiColumn.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("durasi"));

        WatchlistTitleColumn.setCellValueFactory(new PropertyValueFactory<Watchlist, String>("movie"));
        WatchlistLastwatchColumn.setCellValueFactory(new PropertyValueFactory<Watchlist, Integer>("lastWatchPerDurasi"));
        WatchlistFavoriteColumn.setCellValueFactory(new PropertyValueFactory<Watchlist, Boolean>("favorite"));
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
            int result = userDao.addData(new User(
                    0,
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

            User user = lvUser.getSelectionModel().getSelectedItem();

            watchlist = watchlist.filtered(watchlist1 -> watchlist1.getUser().getId().equals(user.getId()));

            table2.setItems(watchlist);
        }
    }
}
