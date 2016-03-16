package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;

import controller.Queries;
import controller.RentalOrderOptions;
import database.JDBC;

public class ViewGUI extends JFrame{
    
    private static ViewGUI instance = null;
    
    private static JPanel queries;
    
    private static JTabbedPane tabs = new JTabbedPane();
    private static JButton connectButton;
    
    public static Container contentPane;
    
    public ViewGUI(){
        super("SnowIO");

        initFrame();
        initTabs();
        initMenus();
        
    }
    private void initFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 100);
        Container contentPane = getContentPane();
        contentPane.setLayout((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new MenuHandler());
        contentPane.add(connectButton, BorderLayout.CENTER);               
        setVisible(true);  
    }

    private void initMenus(){
        MenuBar menu = new MenuBar();
        // File         Database
        //  -Exit           -Connect
        //                  -Disconnect

        Menu fileMenu = new Menu("File");       
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new MenuHandler()); 
        fileMenu.add(exit);                 
        menu.add(fileMenu);

        Menu databaseMenu = new Menu("Database");
        MenuItem connect = new MenuItem("Connect");
        connect.addActionListener(new MenuHandler());
        databaseMenu.add(connect);
        MenuItem disconnect = new MenuItem("Disconnect");
        disconnect.addActionListener(new MenuHandler());
        databaseMenu.add(disconnect);
        menu.add(databaseMenu);

        setMenuBar(menu);
    }
    
    private void initTabs(){
        tabs.addTab("Current Rentals", new CurrentRentalsGUI());
        tabs.addTab("New Rental", new NewRentalGUI());
        tabs.addTab("List Items", new ListItemsGUI());
        tabs.addTab("List Customers", new ListCustomersGUI());
        tabs.addTab("List Employees", new ListEmployees());
    }

    
    private class MenuHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            switch(e.getActionCommand()){
                case "Exit":
                    JDBC jdbc = JDBC.getInstance();
                    if(jdbc.isConnected())
                       displayDialog("Please disconnect from the database before exiting."); 
                    else
                        System.exit(0);
                    break;
                case "Connect":
                    connect();
                    break;
                case "Disconnect":
                    disconnect();
                    break;
            }
        }
    }
    
    private void connect(){
        // Password prompt
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField passwordField = new JPasswordField(10);
        panel.add(label);
        panel.add(passwordField);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Connect",
                                 JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                 null, options, options[0]);
        if(option == 0) // pressing OK button
        {
            String password = String.valueOf(passwordField.getPassword());
            JDBC jdbc = JDBC.getInstance();
            String error = jdbc.openConnection(password);
            if(error.equals(JDBC.SUCCESS)){
                displayDialog("Succesfully connected to the database.");
                Queries.getRentalOrders(new RentalOrderOptions());        
                getContentPane().add(tabs, BorderLayout.CENTER);
                getContentPane().remove(connectButton);
                setSize(800, 600);
                setVisible(true);
            }else if(error.contains("sqlState: 2800"))
                displayDialog("Access denied. Incorrect password.");
            else
                displayDialog("Could not connect to the database\n" + error);
        }
    }
    
    public void displayDialog(String message){
       JOptionPane.showMessageDialog(this, message);
    }
    
    private void disconnect(){
        JDBC jdbc = JDBC.getInstance();
        String error = jdbc.closeConnection();
        if(error.equals(JDBC.SUCCESS)){
            displayDialog("Succesfully disconnected from database.");
            getContentPane().remove(tabs);
            initFrame();
        }else
            displayDialog("Could not disconnect from the database\n" + error);
    }
}
