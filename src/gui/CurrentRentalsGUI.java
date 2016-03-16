package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Queries;
import controller.RentalOrderOptions;
import controller.RentalOrderOptions.Ordering;

public class CurrentRentalsGUI extends JPanel{

    private JTable table;
    
    private RentalOrderOptions tableOptions = new RentalOrderOptions();
    
    public CurrentRentalsGUI() {
        try {
            table = new JTable(TableGUI2.buildTableModel(Queries.getRentalOrders(tableOptions)));
            table.getSelectionModel().addListSelectionListener(new TableHandler());
            table.getTableHeader().addMouseListener(new HeaderHandler());
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
    
    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 780;   
        size.height = 580;  
        return size;
    }
    
    private class TableHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            
        }
    }
    
    private class HeaderHandler implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            int column = table.columnAtPoint(e.getPoint());
            if(column > -1){
                System.out.println(table.getColumnName(column));
                tableOptions.setOrdering(table.getColumnName(column));
                try {
                    table.setModel(TableGUI2.buildTableModel(Queries.getRentalOrders(tableOptions)));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } 
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        
    }
    
    
}
