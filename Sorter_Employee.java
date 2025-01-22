/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import hr_manager_test.HR_manager_test.Department_Name;
import hr_manager_test.HR_manager_test.Role_Level;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rafae
 */
public class Sorter_Employee {
    
    private String filePath; // Path to the file storing employee data

    // Constructor to initialize Sorter_Employee with the file path
    public Sorter_Employee(String filePath) {
        this.filePath = filePath;
    }

    // Main method to load, sort, and display employees
    public void sort_Employees() {
        // Load employees from the file into a list
        List<Employee> employees = load_Employees_From_File();
        
        // If no employees are loaded, or only one, there's nothing to sort
        if (employees == null || employees.size() <= 1) return;

        // Sort the list of employees alphabetically by name using recursive bubble sort
        bubbleSortRecursive(employees, employees.size());
        
        // Display the first 20 employees in the sorted list
        System.out.println("Employees sorted alphabetically. First 20 records:");
        
        // Start printing each employee recursively with index 0
        printEmployeesRecursively(employees, 0);
    }

    // Recursive method for bubble sort
    private void bubbleSortRecursive(List<Employee> employees, int n) {
        // Base case: if the list has only one element or we've sorted all elements
        if (n == 1) {
            return;
        }
        
        // Perform a single pass of bubble sort for the current range (0 to n-1)
        for (int i = 0; i < n - 1; i++) {
            // Compare adjacent elements; if out of order, swap them
            if (employees.get(i).getName().compareToIgnoreCase(employees.get(i + 1).getName()) > 0) {
                // Swap employees[i] and employees[i+1]
                Collections.swap(employees, i, i + 1);
            }
        }
        
        // Recursive call with a reduced range, excluding the last sorted element
        bubbleSortRecursive(employees, n - 1);
    }

    // Recursive helper method to print employees with their index
    private void printEmployeesRecursively(List<Employee> employees, int index) {
        // Base case: stop if we've printed 20 employees or reached the end of the list
        if (index >= employees.size() || index >= 20) {
            return;
        }

        // Print the current employee with an enumerated index
        System.out.println((index + 1) + "° " + employees.get(index));

        // Recursive call to print the next employee in the list
        printEmployeesRecursively(employees, index + 1);
    }

    // Method to load employees from a file and store them in a list
    private List<Employee> load_Employees_From_File() {
        List<Employee> employees = new ArrayList<>(); // List to store loaded employees

        // Open the file and read each line to extract employee details
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Split each line by commas to parse employee attributes
                String[] parts = line.split(", ");
                
                // Check if the line has exactly three parts (name, role, department)
                if (parts.length == 3) {
                    // Extract and clean up employee details
                    String name = parts[0].replace("Employee: ", "").trim();
                    Role_Level managerType = Role_Level.valueOf(parts[1].replace("Role: ", "").trim().toUpperCase());
                    Department_Name department = Department_Name.valueOf(parts[2].replace("Department: ", "").trim().toUpperCase());

                    // Create a new Employee object and add it to the list
                    employees.add(new Employee(name, managerType, department));
                } else {
                    // Print a warning if the line format is incorrect
                    System.out.println("Warning: Skipping improperly formatted line: " + line);
                }
            }
            // Confirm that employees have been successfully loaded from the file
            System.out.println("Successfully loaded " + employees.size() + " employees from file.");
        } catch (FileNotFoundException e) {
            // Handle case where the file is not found
            System.out.println("Error: File not found at path: " + filePath);
        } catch (IOException e) {
            // Handle general input/output errors
            System.out.println("Error: An I/O error occurred while reading the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle incorrect role or department names in the file
            System.out.println("Error: File contains invalid manager type or department name. Please check file format.");
        }
        return employees; // Return the list of employees
    }
}