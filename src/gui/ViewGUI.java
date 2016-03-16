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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.JDBC;

public class ViewGUI extends JFrame{
    
    private static ViewGUI instance = null;
    
    private static JPanel queries;
    
    private static JTabbedPane tabs = new JTabbedPane();
    
    public static Container contentPane;
    
    public ViewGUI(){
        super("SnowIO");

        initFrame();
        initMenus();
        initTabs();
       
    }
        
    private void initFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        Container contentPane = getContentPane();
        contentPane.setLayout((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        contentPane.add(tabs, BorderLayout.CENTER);
        //contentPane.add(new TableGUI());               
        setVisible(true);  
    }

    private void initMenus(){
        MenuBar menu = new MenuBar();
        // File
        //  -Exit

        Menu fileMenu = new Menu("File");       
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new MenuHandler()); 
        fileMenu.add(exit);                 
        menu.add(fileMenu);
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
                    System.exit(0);
                    break;
            }
        }
    }
}
