package main;

import gui.ViewGUI;

import database.JDBC;

public class Main {

    public static void main(String[] args) {

        init_model();
        init_view();
        init_controller();

    }
    // Model: Postgresql Databse Server
    public static void init_model(){
        JDBC jdbc = JDBC.getInstance();
        //jdbc.openConnection();
    }
    
    public static void close_model(){
        JDBC jdbc = JDBC.getInstance();
        jdbc.closeConnection();
    }
    
    //View: GUI input (singleton);
    public static void init_view(){
        ViewGUI gui = new ViewGUI();
    }

    //Controller: Queries
    public static void init_controller(){
        
    }
    
    /*
     * Test the communication!
     *         -- change up the insertSQL statement and check the results on server.
     *                           !!!!   (don't drop tables! )!!!!  
     */
    public static void testConnection(){
        JDBC jdbc = JDBC.getInstance();
        //jdbc.openConnection();

        String insertSQL = "INSERT INTO suppliers VALUES ( 3 , \'Yarden did this from\', \'main method\', 123456 ) ";
        jdbc.update(insertSQL);

        jdbc.closeConnection();

    }

}
