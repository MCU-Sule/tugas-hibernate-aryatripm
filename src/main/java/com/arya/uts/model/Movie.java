package com.arya.uts.model;

public class Movie {
    private Integer id;
    private String title;
    private String genre;
    private Integer durasi;

    public Movie(Integer id, String title, String genre, Integer durasi) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.durasi = durasi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDurasi() {
        return durasi;
    }

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }

    @Override
    public String toString() {
        return title;
    }
}
