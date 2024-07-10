package org.example.dao;

import java.util.List;

public interface BaseDAO<T>{
    public boolean create(T t);
    public boolean update(T t);
    public boolean delete(int id);
    public  T get(int id);
    public List<T> getAll();
}
