package gui;

import java.awt.Component;
import java.awt.Dimension;

public class CurrentRentalsGUI extends Component {

    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.width = 785;   
        size.height = 495;  
        return size;
    }
    
    
}
