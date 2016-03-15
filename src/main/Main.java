package main;

import gui.SuppliersGUI;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import database.JDBC;
import database.Table;


public class Main {

    public static void main(String[] args) {
        
        JDBC jdbc = JDBC.getInstance();
        jdbc.openConnection();
        
        Table suppliersTable = new Table("Suppliers");
        String insertSQL = "INSERT INTO " + suppliersTable.getTableName() + " VALUES ( 3 , \'Yarden did this from\', \'main method\', 123456 ) ";
        suppliersTable.insert(insertSQL);
        
        jdbc.closeConnection();
        
        /*JFrame frame=new JFrame("SnowIO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().add(new SuppliersGUI());
        frame.setSize(640, 360);
        frame.setVisible(true);*/

    }

}
