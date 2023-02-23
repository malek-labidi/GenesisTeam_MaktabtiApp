/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public interface IService<T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(T t);
    public void delete(int id);
    public List<T> getAll();
    public T getOneById(int id);
}
