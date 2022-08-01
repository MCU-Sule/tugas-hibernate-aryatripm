package com.arya.uts.model;

public class Watchlist {
    private Integer id;
    private Integer lastWatch;
    private Boolean favorite;
    private Movie movie;
    private User user;

    public Watchlist(Integer id, Integer lastWatch, Boolean favorite, Movie movie, User user) {
        this.id = id;
        this.lastWatch = lastWatch;
        this.favorite = favorite;
        this.movie = movie;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLastWatch() {
        return lastWatch;
    }

    public void setLastWatch(Integer lastWatch) {
        this.lastWatch = lastWatch;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLastWatchPerDurasi() {
        return lastWatch.toString() + " / " + movie.getDurasi().toString();
    }

    @Override
    public String toString() {
        return "movie=" + movie +
                ", user=" + user;
    }
}
