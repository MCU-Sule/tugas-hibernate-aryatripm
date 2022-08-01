package com.arya.uts.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {
    Integer addData(T data);
    ObservableList<T> getData();
    void updateData(T data);
    Integer delData(T data);
}
