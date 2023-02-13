/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class DataSource {
    
    private final String URL = "jdbc:mysql://localhost:3306/maktabti";
    private final String USER = "root";
    private final String PASSWORD = "";

    Connection cnx;
    public static DataSource instance;

    public Connection getCnx() {
        return cnx;
    }
    
    private DataSource() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to DB");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to DB: " + ex.getMessage());
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
}
