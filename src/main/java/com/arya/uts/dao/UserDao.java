package com.arya.uts.dao;

import com.arya.uts.model.UserEntity;
import com.arya.uts.utility.HibernateUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class UserDao implements DaoInterface<UserEntity> {

    @Override
    public Integer addData(UserEntity data) {
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
    public ObservableList<UserEntity> getData() {
        ObservableList<UserEntity> users = FXCollections.observableArrayList();
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        query.from(UserEntity.class);

        users.addAll(session.createQuery(query).getResultList());
        session.close();

        return users;
    }

    @Override
    public void updateData(UserEntity data) {
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
    public Integer delData(UserEntity data) {
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
