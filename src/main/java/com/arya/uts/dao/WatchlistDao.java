package com.arya.uts.dao;


import com.arya.uts.model.MovieEntity;
import com.arya.uts.model.WatchListEntity;
import com.arya.uts.utility.HibernateUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class WatchlistDao implements DaoInterface<WatchListEntity> {


    @Override
    public Integer addData(WatchListEntity data) {
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
    public ObservableList<WatchListEntity> getData() {
        ObservableList<WatchListEntity> watchList = FXCollections.observableArrayList();
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WatchListEntity> query = builder.createQuery(WatchListEntity.class);
        query.from(WatchListEntity.class);

        watchList.addAll(session.createQuery(query).getResultList());
        session.close();
        return watchList;
    }

    @Override
    public void updateData(WatchListEntity data) {
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
    public Integer delData(WatchListEntity data) {
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
}
