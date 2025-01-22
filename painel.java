/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author rafae
 */
public class painel {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("HR Management Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        
        // Add menu items
        JMenuItem addEmployee = new JMenuItem("Add Employee");
        JMenuItem viewByDepartment = new JMenuItem("View by Department");
        JMenuItem exitApp = new JMenuItem("Exit");
        
        menu.add(addEmployee);
        menu.add(viewByDepartment);
        menu.add(exitApp);
        menuBar.add(menu);
        
        // Set menu bar to the frame
        frame.setJMenuBar(menuBar);
        
        // Create default panel
        JPanel defaultPanel = new JPanel();
        defaultPanel.add(new JLabel("Welcome to HR Management Application!"));
        frame.add(defaultPanel);
        
        // Set the frame to be visible
        frame.setVisible(true);
        
        // Action listeners for menu items
        addEmployee.addActionListener(e -> {
            frame.setContentPane(new AddEmployeePanel());
            frame.validate();
        });
        
        viewByDepartment.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.add(new JLabel("View by Department Feature Coming Soon!"));
            frame.setContentPane(panel);
            frame.validate();
        });
        
        exitApp.addActionListener(e -> System.exit(0));
    }
}

class AddEmployeePanel extends JPanel {
    public AddEmployeePanel() {
        setLayout(new GridLayout(4, 2, 10, 10));
        
        // Add fields
        add(new JLabel("Employee Name:"));
        JTextField nameField = new JTextField();
        add(nameField);
        
        add(new JLabel("Role:"));
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Manager", "Supervisor", "Technician"});
        add(roleBox);
        
        add(new JLabel("Department:"));
        JComboBox<String> deptBox = new JComboBox<>(new String[]{"HR", "Sales", "Development", "Projects"});
        add(deptBox);
        
        JButton saveButton = new JButton("Save");
        add(saveButton);
        
        // Add action listener for Save
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String role = (String) roleBox.getSelectedItem();
            String department = (String) deptBox.getSelectedItem();
            
            // Call method to save employee
            JOptionPane.showMessageDialog(this, "Saved: " + name + ", " + role + ", " + department);
        });
    }
}

