package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

//import net.miginfocom.swing.MigLayout;

public class ignore extends JPanel {

   private JTextField idField = new JTextField(10);
   private JTextField nameField = new JTextField(30);
   private JTextField addressField = new JTextField(30);

   //... mNameField, lNameField, emailField, phoneField

   private JButton createButton = new JButton("New...");
   private JButton updateButton = new JButton("Update");
   private JButton deleteButton = new JButton("Delete");
   
  // private Table supplierTable = new Table("Suppliers");

   public ignore() {
      setBorder(new TitledBorder
      (new EtchedBorder(),"Suppliers Details"));
      setLayout(new BorderLayout(5, 5));
      add(initFields(), BorderLayout.NORTH);
      add(initButtons(), BorderLayout.CENTER);
   }
   
   public Dimension getPreferredSize(){
       Dimension size = super.getPreferredSize();
       size.width = 785;   
       size.height = 495;  
       return size;
   }

   private JPanel initButtons() {
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
      panel.add(createButton);
      createButton.addActionListener((ActionListener) new ButtonHandler());
      panel.add(updateButton);
      updateButton.addActionListener((ActionListener) new ButtonHandler());
      panel.add(deleteButton);
      deleteButton.addActionListener((ActionListener) new ButtonHandler());
      return panel;
   }

   private JPanel initFields() {
      JPanel panel = new JPanel();
      //panel.setLayout(new MigLayout());
      panel.add(new JLabel("ID"), "align label");
      panel.add(idField, "wrap");
      idField.setEnabled(false);
      panel.add(new JLabel("Name"), "align label");
      panel.add(nameField, "wrap");
      panel.add(new JLabel("Address"), "align label");
      panel.add(addressField, "wrap");
      return panel;
   }

   /*private Supplier getFieldData() {
      Supplier s = new Supplier();
      s.setName(nameField.getText());
      s.setAddress(addressField.getText());
      return s;
   }

   private void setFieldData(Supplier s) {
      idField.setText(String.valueOf(s.getSupplierID()));
      nameField.setText(s.getName());
      addressField.setText(s.getAddress());
   }*/

   private boolean isEmptyFieldData() {
      return (nameField.getText().trim().isEmpty()
         && addressField.getText().trim().isEmpty());
   }

   private class ButtonHandler implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         String insertSQL = null, updateSQL = null;
         switch (e.getActionCommand()) {
         case "Save":
            if (isEmptyFieldData()) {
               JOptionPane.showMessageDialog(null,
               "Cannot create an empty record");
               return;
            }
            //supplierTable.insert(insertSQL);
            JOptionPane.showMessageDialog(null, "New supplier created successfully.");
            createButton.setText("New...");
            break;
         case "New...":
            initFields();
            createButton.setText("Save");
            break;
         case "Update":
            if (isEmptyFieldData()) {
               JOptionPane.showMessageDialog(null, "Cannot update an empty record");
               return;
            }
            //supplierTable.update(updateSQL);
            JOptionPane.showMessageDialog(null,"Supplier is updated successfully");
               break;
         /*case "Delete":
            if (isEmptyFieldData()) {
               JOptionPane.showMessageDialog(null, "Cannot delete an empty record");
               return;
            }
            p = bean.getCurrent();
            bean.delete();
            JOptionPane.showMessageDialog(
               null,"Person with ID:"
               + String.valueOf(p.getPersonId()
               + " is deleted successfully"));
               break;*/
         default:
            JOptionPane.showMessageDialog(null,
            "invalid command");
         }
      }
   }
}