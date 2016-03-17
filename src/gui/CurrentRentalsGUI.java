package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ItemSelectable;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
    
    // Controller swing..
    private JTextField customerField, employeeField;
    private JCheckBox currentOrders;
    private JCheckBox pastOrders;
    private JButton moreInfo;
    
    private RentalOrderOptions tableOptions = new RentalOrderOptions();
    
    public CurrentRentalsGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        //add(initButtons(), BorderLayout.WEST);
        add(initButtons(), c);
        c.gridx = 1;
        c.gridy = 0;
        //add(initFiltering(), BorderLayout.NORTH);
        add(initFiltering(), c);
        c.gridx = 1;
        c.gridy = 1;
        //add(initTable(), BorderLayout.CENTER);
        add(initTable(), c);
    }
    
    /**
     * fetches new table form database
     *          -- fetches query from RentalOrderOptions
     */
    private void updateTable(){
        try {
            table.setModel(TableGUI.buildTableModel(Queries.getRentalOrders(tableOptions)));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    } 
    
    //-----------------------------------
    //            --- GUI ----
    //-----------------------------------

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
    
    public JComponent initFiltering(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel.setBorder(new TitledBorder(new EtchedBorder(),"Filter results.."));
        JLabel customerLabel = new JLabel("Customer Name:");
        c.gridx=1;
        c.gridy=0;
        panel.add(customerLabel, c);
        customerField = new JTextField(20);
        customerField.getDocument().addDocumentListener(new CustomerFieldHandler());
        c.gridx=1;
        c.gridy=1;
        panel.add(customerField, c);
        JLabel employeeLabel = new JLabel("Employee Name:");
        c.gridx=1;
        c.gridy=2;
        panel.add(employeeLabel, c);
        employeeField = new JTextField(20);
        employeeField.getDocument().addDocumentListener(new EmployeeFieldHandler());
        c.gridx=1;
        c.gridy=3;
        panel.add(employeeField, c);
        
        currentOrders = new JCheckBox("Current Orders", true);
        currentOrders.addItemListener(new CheckBoxHandler());
        c.gridx=0;
        c.gridy=0;
        panel.add(currentOrders, c);
        
        pastOrders = new JCheckBox("Past Orders", true);
        pastOrders.addItemListener(new CheckBoxHandler());
        c.gridx=0;
        c.gridy=1;
        panel.add(pastOrders, c); 
        
        
        
        return panel;
    }
    
    public JComponent initButtons(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel.setBorder(new TitledBorder(new EtchedBorder(),"Toolbar"));

        moreInfo = new JButton("More info..");
        moreInfo.addActionListener(new ButtonHandler());
        c.gridx=0;
        c.gridy=0;
        panel.add(moreInfo, c);
        
        return panel;
    }
   
    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 780;   
        size.height = 580;  
        return size;
    }
    
    //-----------------------------------
    //       --- Event Handlers ----
    //-----------------------------------
    
    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
           }
        
    }
    
    private class TableHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel model = (ListSelectionModel) e.getSource();

            if (model.isSelectionEmpty()) {
                System.out.println(" <none>");
            } else {
                // Find out which indexes are selected.
                int minIndex = model.getMinSelectionIndex();
                int maxIndex = model.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (model.isSelectedIndex(i)) {
                        System.out.println(" " + i);
                    }
                }
            }
            System.out.println();
        }
    }

    
    private class CheckBoxHandler implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
           ItemSelectable checkBox = e.getItemSelectable();
           if(checkBox == currentOrders)
               tableOptions.toggleCurrentOrders();
           else if(checkBox == pastOrders)
               tableOptions.togglePastOrders();
           updateTable();
        }
        
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
}
