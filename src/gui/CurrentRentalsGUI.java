package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Queries;
import controller.RentalOrderOptions;
import controller.RentalOrderOptions.Ordering;

public class CurrentRentalsGUI extends JPanel{

    private JTable table;
    
    private JTextField customerField, employeeField;
    
    private RentalOrderOptions tableOptions = new RentalOrderOptions();
    
    public CurrentRentalsGUI() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
        add(initButtons(), BorderLayout.NORTH);
        add(initTable(), BorderLayout.CENTER);
    }
    
    public JComponent initTable(){
        JScrollPane scrollPane = null;
        try {
            table = new JTable(TableGUI.buildTableModel(Queries.getRentalOrders(tableOptions)));
            table.getSelectionModel().addListSelectionListener(new TableHandler());
            table.getTableHeader().addMouseListener(new HeaderHandler());
            scrollPane = new JScrollPane(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return scrollPane;
    }
    
    public JComponent initButtons(){
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(),"Filter results.."));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel customerLabel = new JLabel("Customer Name");
        customerField = new JTextField(20);
        customerField.getDocument().addDocumentListener(new CustomerFieldHandler());
        panel.add(customerLabel, BorderLayout.NORTH);
        panel.add(customerField);
        JLabel employeeLabel = new JLabel("Employee Name");
        employeeField = new JTextField(20);
        employeeField.getDocument().addDocumentListener(new EmployeeFieldHandler());
        panel.add(employeeLabel, BorderLayout.NORTH);
        panel.add(employeeField);
        return panel;
    }
    
    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 780;   
        size.height = 580;  
        return size;
    }
    
    private class CustomerFieldHandler implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            tableOptions.setCustomerName(customerField.getText());
            updateTable();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            tableOptions.setCustomerName("");
            updateTable(); 
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
        }
        
    }
    private class EmployeeFieldHandler implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            tableOptions.setEmployeeName(employeeField.getText());
            updateTable();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            tableOptions.setEmployeeName("");
            updateTable(); 
            
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
        }
        
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
                updateTable();
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
    
    private void updateTable(){
        try {
            table.setModel(TableGUI.buildTableModel(Queries.getRentalOrders(tableOptions)));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    
    
}
