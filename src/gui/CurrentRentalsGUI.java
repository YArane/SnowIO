package gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.Queries;
import controller.RentalOrderOptions;

public class CurrentRentalsGUI extends JPanel{

    public CurrentRentalsGUI() {
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        scrollPane.add(table);
        add(scrollPane);
        

    }
    
    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 780;   
        size.height = 580;  
        return size;
    }
    
    
    
}
