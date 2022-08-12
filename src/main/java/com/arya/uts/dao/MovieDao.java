package com.arya.uts.dao;

import com.arya.uts.model.MovieEntity;
import com.arya.uts.utility.HibernateUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class MovieDao implements DaoInterface<MovieEntity>{

    @Override
    public Integer addData(MovieEntity data) {
        int result = 0;
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(data);
            transaction.commit();
            result = 1;
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
        return result;
    }

    @Override
    public ObservableList<MovieEntity> getData() {
        ObservableList<MovieEntity> movies = FXCollections.observableArrayList();
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MovieEntity> query = builder.createQuery(MovieEntity.class);
        query.from(MovieEntity.class);

        movies.addAll(session.createQuery(query).getResultList());
        session.close();
        return movies;
    }

    @Override
    public void updateData(MovieEntity data) {
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(data);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public Integer delData(MovieEntity data) {
        int result = 0;
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(data);
            transaction.commit();
            result = 1;
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
        return result;
    }

    public ObservableList<String> getGenres() {
        ObservableList<MovieEntity> movies = FXCollections.observableArrayList();
        ObservableList<String> genres = FXCollections.observableArrayList();

        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MovieEntity> query = builder.createQuery(MovieEntity.class);
        query.from(MovieEntity.class);

        movies.addAll(session.createQuery(query).getResultList());
        session.close();

        for (MovieEntity movie : movies) {
            String genre1 = movie.getGenre();
            String[] genre2 = genre1.split("[,]", 0);
            for (String g: genre2) {
                if (!genres.contains(g.trim())) {
                    genres.add(g.trim());
                }
            }
        }

        return genres;
    }
}
