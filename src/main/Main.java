package main;

import gui.SuppliersGUI;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) {
        
        
        
        JFrame frame=new JFrame("SnowIO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().add(new SuppliersGUI());
        frame.setSize(640, 360);
        frame.setVisible(true);

    }

}
