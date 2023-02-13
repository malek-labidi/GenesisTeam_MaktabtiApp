/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.main;

import edu.esprit.util.DataSource;
import java.sql.Connection;

/**
 *
 * @author admin
 */
public class Main {
    public static void main(String[] args) {
        Connection ds= DataSource.getInstance().getCnx();
    }
}
