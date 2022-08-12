package com.arya.uts.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Movie", schema = "utsSA")
public class MovieEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idMovie")
    private Integer idMovie;
    @Basic
    @Column(name = "Title")
    private String title;
    @Basic
    @Column(name = "Genre")
    private String genre;
    @Basic
    @Column(name = "Durasi")
    private Integer durasi;

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(idMovie, that.idMovie) && Objects.equals(title, that.title) && Objects.equals(genre, that.genre) && Objects.equals(durasi, that.durasi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMovie, title, genre, durasi);
    }

    @Override
    public String toString() {
        return title;
    }
}
