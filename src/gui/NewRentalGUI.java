package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Queries;
import controller.RentalOrderOptions;

public class NewRentalGUI extends JPanel {

    private JTable table;

    // Controller swing
    private JButton createNewCustomer;

    private RentalOrderOptions tableOptions = new RentalOrderOptions();

    public NewRentalGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        //add(initButtons(), BorderLayout.WEST);
        add(initButtons(), c);
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

    public JComponent initButtons(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel.setBorder(new TitledBorder(new EtchedBorder(),"Toolbar"));

        createNewCustomer = new JButton("Create New Customer");
        createNewCustomer.addActionListener(new ButtonHandler());
        c.gridx=0;
        c.gridy=0;
        panel.add(createNewCustomer, c);

        return panel;
    }

    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 780;
        size.height = 580;
        return size;
    }

    public void showNewCustomerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.gridx=1;
        cp.gridy=0;
        JLabel label = new JLabel("Enter the information shown below to add a new customer");
        panel.add(label, cp);

        String[] customerDataFields = new String[] {
                "Customer Name", "Customer Address",
                "Customer Age", "Phone Number", "Weight",
                "Height", "Credit Card Number", "Credit Card Type",
                "Cardholder Name", "Billing Address", "CVV"
        };

        for (String fieldId: customerDataFields) {
            cp.gridy = cp.gridy + 1;
            JLabel fieldLabel = new JLabel(fieldId + ":");
            panel.add(fieldLabel, cp);
            JTextField newField = new JTextField(20);
            newField.setName(fieldId);
            cp.gridx = 2;
            panel.add(newField, cp);
            cp.gridx = 1;
        }

        String[] options = new String[]{"Create Customer", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Create New Customer",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    //-----------------------------------
    //       --- Event Handlers ----
    //-----------------------------------

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == null) return;
            System.out.println(e.getActionCommand());
            if (e.getActionCommand().equals("Create New Customer")) {
                showNewCustomerPanel();
            }
        }
    }

}
