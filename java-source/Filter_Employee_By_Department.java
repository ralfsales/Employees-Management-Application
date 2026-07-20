/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import hr_manager_test.HR_manager_test.Department_Name;
import hr_manager_test.HR_manager_test.Role_Level;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rafae
 */

// Create a Class for filtering and displaying employees by department from a file containing employee data.
public class Filter_Employee_By_Department {

    private String filePath; 
    // Stores the path to the file where employee data is located.
    // This field allows methods in this class to access and read the specified file.

    // Constructor: Initializes the filePath field with the specified path
    public Filter_Employee_By_Department(String filePath) { 
        this.filePath = filePath; 
        // Sets the file path for this instance, enabling it to access the specified file for employee filtering operations.
    }

    // Method to filter and display employees by a selected department
    public void employees_By_Department(Scanner scanner) { 
        // Displays a list of employees in the selected department, based on user input.

        System.out.println("Select Department to view employees:");
        for (int i = 0; i < Department_Name.values().length; i++) { 
            System.out.println((i + 1) + ". " + Department_Name.values()[i]); 
            // Lists each department option with a number for easy selection by the user.
        }

        int departmentChoice = 0; 
        boolean validInput = false; 

        // Loop until a valid input is provided
        while (!validInput) { 
            // Repeats until the user enters a valid department choice (1-4).
            
            System.out.print("Enter your choice (1-4): ");
            if (scanner.hasNextInt()) { 
                // Checks if the user input is an integer.
                
                departmentChoice = scanner.nextInt();
                scanner.nextLine(); // Consumes the newline character left after nextInt.
                
                if (departmentChoice >= 1 && departmentChoice <= 4) { 
                    // Validates that the choice is within the department range.
                    
                    validInput = true; // Marks input as valid to exit the loop.
                } else {
                    System.out.println("Invalid choice, please select a number between 1 and 4.");
                    // Provides feedback if the input is out of range.
                }
            } else {
                System.out.println("Invalid input, please enter an integer (1 to 4).");
                scanner.nextLine(); // Clears invalid input if the user does not enter an integer.
            }
        }

        Department_Name selectedDepartment = Department_Name.values()[departmentChoice - 1]; 
        // Retrieves the department corresponding to the user’s choice.

        List<Employee> employees = load_Employees_From_File(); 
        // Loads the current list of employees from the file.

        System.out.println("Employees in " + selectedDepartment + " Department:"); 
        // Prints a header showing the selected department.

        employees.stream()
                .filter(emp -> emp.getDepartment() == selectedDepartment) 
                // Filters employees based on whether their department matches the selected department.
                
                .forEach(System.out::println); 
                // Prints each employee in the selected department.
    }

    // Helper method to load all employees from the file
    private List<Employee> load_Employees_From_File() { 
        // Reads employee data from the file and returns a list of Employee objects.

        List<Employee> employees = new ArrayList<>(); 
        // Initializes an empty list to store Employee objects as they are loaded from the file.

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { 
            // Opens the file specified by filePath for reading, using BufferedReader for efficient line-by-line reading.
            // Try-with-resources is used here to automatically close the reader after use.

            String line; 
            while ((line = reader.readLine()) != null) { 
                // Reads each line of the file until the end (when readLine() returns null).

                String[] parts = line.split(", "); 
                // Splits each line into parts by ", " to separate name, role, and department.

                if (parts.length == 3) { 
                    // Checks if the line has exactly three parts to ensure proper format.

                    String name = parts[0].replace("Employee: ", "").trim(); 
                    // Extracts and trims the employee name, removing the "Employee: " prefix.

                    Role_Level managerType = Role_Level.valueOf(parts[1].replace("Role: ", "").trim().toUpperCase()); 
                    // Extracts and converts the role to the Role_Level enum, handling valid role values only.

                    Department_Name department = Department_Name.valueOf(parts[2].replace("Department: ", "").trim().toUpperCase()); 
                    // Extracts and converts the department to the Department_Name enum, handling valid department values only.

                    employees.add(new Employee(name, managerType, department)); 
                    // Adds a new Employee object to the employees list with the parsed data.
                }
            }
        } catch (IOException e) { 
            // Catches any I/O exceptions that may occur during file reading, such as file not found or read errors.

            System.out.println("An error occurred while loading employees from the file: " + e.getMessage()); 
            // Provides feedback on the error, helping with troubleshooting file access issues.
        }
        return employees; 
        // Returns the list of employees loaded from the file.
    }
}

