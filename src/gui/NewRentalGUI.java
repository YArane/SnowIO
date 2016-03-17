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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.CustomerOptions;
import controller.Queries;
import controller.RentalOrderOptions;

public class NewRentalGUI extends JPanel {

    private JTable table;

    // Controller swing
    private JButton createNewCustomer, lookUpCustomer;

    // Global for table search
    private JTextField customerNameField;

    // Global used to store the rows for the tables used in the views
    private ResultSet customerTuples;
    private ResultSet itemTuples;

    // Used Keep track of the Customer selected by the user
    private int selectedCustomerID;

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
    private void updateTable(String searchQuery){
        try {
            if (searchQuery == null) {
                customerTuples = Queries.getCustomers();
            } else {
                customerTuples = Queries.getCustomers(searchQuery);
            }
            table.setModel(TableGUI.buildTableModel(customerTuples));
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

        lookUpCustomer = new JButton("Look Up Existing Customer");
        lookUpCustomer.addActionListener(new ButtonHandler());
        c.gridy=1;
        panel.add(lookUpCustomer, c);

        return panel;
    }

    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 780;
        size.height = 580;
        return size;
    }

    public void showMessagePopup(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.gridx=1;
        cp.gridy=0;
        JLabel label = new JLabel(message);
        panel.add(label, cp);

        String[] options = new String[]{"Continue"};
        int option = JOptionPane.showOptionDialog(null, panel, "Create New Customer",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

    }

    private void showNewCustomerPanel() {
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

        Map<String, JTextField> fieldMap = new HashMap<String, JTextField>();
        for (String fieldId: customerDataFields) {
            cp.gridy = cp.gridy + 1;
            JLabel fieldLabel = new JLabel(fieldId + ":");
            panel.add(fieldLabel, cp);
            JTextField newField = new JTextField(20);
            newField.setName(fieldId);
            cp.gridx = 2;
            panel.add(newField, cp);
            cp.gridx = 1;
            fieldMap.put(fieldId, newField);
        }

        String[] options = new String[]{"Create Customer", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Create New Customer",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);


        if (option == 0) { // Create Account
            CustomerOptions customerOpts = new CustomerOptions();

            boolean emptyFieldFound = false;
            for (JTextField tfield : fieldMap.values()) {

                if (tfield.getText().equals("")) {
                    emptyFieldFound = true;
                    break;
                }

                switch (tfield.getName()) {
                    case "Customer Name":
                        customerOpts.setCustomerName(tfield.getText());
                        break;
                    case "Customer Address":
                        customerOpts.setCustomerAddress(tfield.getText());
                        break;
                    case "Customer Age":
                        customerOpts.setCustomerAge(tfield.getText());
                        break;
                    case "Phone Number":
                        customerOpts.setCustomerPhone(tfield.getText());
                        break;
                    case "Weight":
                        customerOpts.setWeight(tfield.getText());
                        break;
                    case "Height":
                        customerOpts.setHeight(tfield.getText());
                        break;
                    case "Credit Card Number":
                        customerOpts.setCreditCardNumber(tfield.getText());
                        break;
                    case "Credit Card Type":
                        customerOpts.setCreditCardType(tfield.getText());
                        break;
                    case "Cardholder Name":
                        customerOpts.setCardholderName(tfield.getText());
                        break;
                    case "Billing Address":
                        customerOpts.setBillingAddress(tfield.getText());
                        break;
                    case "CVV":
                        customerOpts.setCVV(tfield.getText());
                        break;
                    default:
                        System.out.println("Something is wrong!");
                }
            }

            // TODO: there is no error handling in place! We need to refactor the JDBC endpoints
            // TODO: There is no way to know if an insert doesn't work
            if (!emptyFieldFound) {
                String insertResult = Queries.insertNewCustomer(customerOpts);
                showMessagePopup("New customer insertion result: " + insertResult);
            } else {
                showMessagePopup("Customer account could not be created. Please fill out all fields.");
            }
        }
    }

    private void showExistingCustomerList() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.gridx=1;
        cp.gridy=0;
        JLabel label = new JLabel("Click on the customer who is placing a rental");
        panel.add(label, cp);

        JLabel customerNameLabel = new JLabel("Customer Name:");
        cp.gridy=1;
        panel.add(customerNameLabel, cp);
        cp.gridx=2;
        customerNameField = new JTextField(20);
        customerNameField.getDocument().addDocumentListener(new CustomerFieldHandler());
        panel.add(customerNameField, cp);

        cp.gridy=2;
        panel.add(initTable(), cp);

        String[] options = new String[]{"Continue", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Registered Customers",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) { // Continue
            showItemsAvailableForRent();
        }
    }

    private void showItemsAvailableForRent() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.gridx=1;
        cp.gridy=0;
        JLabel label = new JLabel("Select the items that are being rented");
        panel.add(label, cp);

        String[] options = new String[]{"Place Rental", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Rental Items Menu",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);


    }

    public JComponent initTable(){
        JScrollPane scrollPane = null;
        try {
            customerTuples = Queries.getCustomers();
            table = new JTable(TableGUI.buildTableModel(Queries.getCustomers()));
            table.getSelectionModel().addListSelectionListener(new TableHandler());
            scrollPane = new JScrollPane(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scrollPane;
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
            } else if (e.getActionCommand().equals("Look Up Existing Customer")) {
                showExistingCustomerList();
            }

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
                        selectedCustomerID = (int) table.getValueAt(i,0);
                    }
                }
            }
        }
    }

    private class CustomerFieldHandler implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateTable(customerNameField.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateTable(customerNameField.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
        }

    }

}
