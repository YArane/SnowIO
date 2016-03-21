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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

//import com.sun.tools.javac.util.Name;

import com.sun.tools.javac.util.Name;
import controller.BillingInfoOptions;
import controller.CustomerOptions;
import controller.Queries;
import controller.RentalOrderOptions;

public class NewRentalGUI extends JPanel {

    private JTable table, bootsTable, polesTable, skisTable, employeesTable;

    // Controller swing
    private JButton createNewCustomer, lookUpCustomer;

    // Global for table search
    private JTextField customerNameField;

    // Global used to store the rows for the tables used in the views
    private ResultSet customerTuples, bootTuples, skiTuples, poleTuples, employeeTuples;

    // Used Keep track of the Customer selected by the user
    private int selectedCustomerID, selectedEmployeeID;

    // List containing the ID's of the items that will be rented
    private ArrayList<Integer> itemsToBeRented = new ArrayList<>();

    private RentalOrderOptions tableOptions = new RentalOrderOptions();

    // Used to determine the type of table to be displayed
    private enum TableType {CUSTOMERS, BOOTSFORRENT, POLESFORRENT, SKISFORRENT, EMPLOYEES};

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
    private void updateTable(CustomerOptions customerOpts){
        try {
            customerTuples = Queries.getCustomers(customerOpts);
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
            BillingInfoOptions billingOpts = new BillingInfoOptions();

            boolean emptyFieldFound = false;
            for (JTextField tfield : fieldMap.values()) {

                if (tfield.getText().equals("")) {
                    emptyFieldFound = true;
                    break;
                }

                switch (tfield.getName()) {
                    case "Customer Name":
                        customerOpts.setName(tfield.getText());
                        break;
                    case "Customer Address":
                        customerOpts.setAddress(tfield.getText());
                        break;
                    case "Customer Age":
                        customerOpts.setAge(tfield.getText());
                        break;
                    case "Phone Number":
                        customerOpts.setPhone(tfield.getText());
                        break;
                    case "Weight":
                        customerOpts.setWeight(tfield.getText());
                        break;
                    case "Height":
                        customerOpts.setHeight(tfield.getText());
                        break;
                    case "Credit Card Number":
                        customerOpts.setCreditCardNumber(tfield.getText());
                        billingOpts.setCreditCardNumber(tfield.getText());
                        break;
                    case "Credit Card Type":
                        billingOpts.setType(tfield.getText());
                        break;
                    case "Cardholder Name":
                    	billingOpts.setName(tfield.getText());
                        break;
                    case "Billing Address":
                    	billingOpts.setAddress(tfield.getText());
                        break;
                    case "CVV":
                    	billingOpts.setCVV(tfield.getText());
                        break;
                    default:
                        System.out.println("Something is wrong!");
                }
            }

            // TODO: there is no error handling in place! We need to refactor the JDBC endpoints
            // TODO: There is no way to know if an insert doesn't work
            if (!emptyFieldFound) {
                String insertResult = Queries.insertNewCustomer(customerOpts, billingOpts);
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
        panel.add(initTable(TableType.CUSTOMERS), cp);

        String[] options = new String[]{"Continue", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Registered Customers",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) { // Continue
            if (selectedCustomerID == -1) {
                showMessagePopup("Please select a customer.");
            } else {
                showItemsAvailableForRent();
            }
        }
    }

    private void showItemsAvailableForRent() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.gridx=1;
        cp.gridy=0;
        JLabel label = new JLabel("Select the items that are being rented");
        panel.add(label, cp);

        cp.gridy=2;
        cp.gridx=1;
        panel.add(initTable(TableType.SKISFORRENT), cp);

        cp.gridx=2;
        panel.add(initTable(TableType.POLESFORRENT), cp);

        cp.gridx=3;
        panel.add(initTable(TableType.BOOTSFORRENT), cp);

        String[] options = new String[]{"Continue", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Rental Items Menu",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) {

            if (itemsToBeRented.size() == 0) {
                showMessagePopup("Please select at least one item to be rented.");
                return;
            }

            showRentalOrderOptionsMenu();
        }
    }

    private void showRentalOrderOptionsMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.gridx=1;
        cp.gridy=0;
        JLabel label = new JLabel("Enter the information shown below to place the rental order");
        panel.add(label, cp);

        String[] rentalOrderDataFields = new String[] {
                "Date Out", "Total Price"
        };

        Map<String, JTextField> fieldMap = new HashMap<String, JTextField>();
        for (String fieldId: rentalOrderDataFields) {
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

        cp.gridy = cp.gridy + 1;
        JLabel specifyEmployeeLabel = new JLabel("Employee processing order:");
        panel.add(specifyEmployeeLabel, cp);

        cp.gridy = cp.gridy + 1;
        panel.add(initTable(TableType.EMPLOYEES), cp);

        String[] options = new String[]{"Place Rental Order", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Create New Customer",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) {
            RentalOrderOptions rentalOrderOpts = new RentalOrderOptions();
            boolean emptyFieldFound = false;
            for (JTextField tfield : fieldMap.values()) {

                if (tfield.getText().equals("")) {
                    emptyFieldFound = true;
                    break;
                }

                switch (tfield.getName()) {
                    case "Date Out":
                        rentalOrderOpts.setDateOut(tfield.getText());
                        break;
                    case "Total Price":
                        rentalOrderOpts.setTotalPrice(tfield.getText());
                        break;
                    default:
                        System.out.println("Something is wrong!");
                }
            }

            if (selectedEmployeeID == -1) {
                showMessagePopup("Please select an employee.");
                return;
            } else {
                rentalOrderOpts.setEmployeeID(Integer.toString(selectedEmployeeID));
            }

            rentalOrderOpts.setCustomerID(Integer.toString(selectedCustomerID));

            if (!emptyFieldFound) {
                Queries.insertRentalOrder(rentalOrderOpts);
                int rentalOrderID = Queries.getRentalOrderID(rentalOrderOpts);
                String insertResult = Queries.addItemsToRentalOrder(itemsToBeRented, rentalOrderID);
                showMessagePopup("Rental order placed status: " + insertResult);
            } else {
                showMessagePopup("Order Could not be placed. An error occurred. Please make sure to provide all the fields.");
            }
        }
    }

    public JComponent initTable(TableType tableType){
        JScrollPane scrollPane = null;
        try {
            if (tableType == TableType.CUSTOMERS) {
            	CustomerOptions customerOpts = new CustomerOptions();
                selectedCustomerID = -1;
                customerTuples = Queries.getCustomers(customerOpts);
                table = new JTable(TableGUI.buildTableModel(customerTuples));
                table.getSelectionModel().addListSelectionListener(new TableHandler(TableType.CUSTOMERS));
                scrollPane = new JScrollPane(table);
            } else if (tableType == TableType.BOOTSFORRENT) {
                bootTuples = Queries.getBootsForRent();
                bootsTable = new JTable(TableGUI.buildTableModel(bootTuples));
                bootsTable.getSelectionModel().addListSelectionListener(new TableHandler(TableType.BOOTSFORRENT));
                scrollPane = new JScrollPane(bootsTable);
            } else if (tableType == TableType.POLESFORRENT) {
                poleTuples = Queries.getPolesForRent();
                polesTable = new JTable(TableGUI.buildTableModel(poleTuples));
                polesTable.getSelectionModel().addListSelectionListener(new TableHandler(TableType.POLESFORRENT));
                scrollPane = new JScrollPane(polesTable);
            } else if (tableType == TableType.SKISFORRENT) {
                skiTuples = Queries.getSkisForRent();
                skisTable = new JTable(TableGUI.buildTableModel(skiTuples));
                skisTable.getSelectionModel().addListSelectionListener(new TableHandler(TableType.SKISFORRENT));
                scrollPane = new JScrollPane(skisTable);
            } else if (tableType == TableType.EMPLOYEES) {
                selectedEmployeeID = -1;
                employeeTuples = Queries.getEmployees();
                employeesTable = new JTable(TableGUI.buildTableModel(employeeTuples));
                employeesTable.getSelectionModel().addListSelectionListener(new TableHandler(TableType.EMPLOYEES));
                scrollPane = new JScrollPane(employeesTable);
            }
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

        private TableType tableType;

        public TableHandler(TableType tableType) {
            this.tableType = tableType;
        }

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
                    if (tableType == TableType.CUSTOMERS) {
                        if (model.isSelectedIndex(i)) {
                            selectedCustomerID = (int) table.getValueAt(i,0);
                        }
                    } else if (tableType == TableType.SKISFORRENT) {
                        if (model.isSelectedIndex(i)) {
                            itemsToBeRented.add((int) skisTable.getValueAt(i,0));
                            ((DefaultTableModel) skisTable.getModel()).removeRow(i);
                        }
                    } else if (tableType == TableType.BOOTSFORRENT) {
                        if (model.isSelectedIndex(i)) {
                            itemsToBeRented.add((int) bootsTable.getValueAt(i,0));
                            ((DefaultTableModel) bootsTable.getModel()).removeRow(i);
                        }
                    } else if (tableType == TableType.POLESFORRENT) {
                        if (model.isSelectedIndex(i)) {
                            itemsToBeRented.add((int) polesTable.getValueAt(i,0));
                            ((DefaultTableModel) polesTable.getModel()).removeRow(i);
                        }
                    } else if (tableType == TableType.EMPLOYEES) {
                        if (model.isSelectedIndex(i)) {
                            selectedEmployeeID = (int) table.getValueAt(i,0);
                        }
                    }
                }
            }
        }
    }

    private class CustomerFieldHandler implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
        	CustomerOptions customerOpts = new CustomerOptions();
        	customerOpts.setName(customerNameField.getText());
            updateTable(customerOpts);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        	CustomerOptions customerOpts = new CustomerOptions();
        	customerOpts.setName(customerNameField.getText());
            updateTable(customerOpts);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
        }

    }

}
