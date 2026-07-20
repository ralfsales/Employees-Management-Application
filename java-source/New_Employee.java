/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import hr_manager_test.HR_manager_test.Department_Name;
import hr_manager_test.HR_manager_test.Role_Level;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rafae
 */
public class New_Employee {
    private List<Employee> employeeList; // Holds the list of Employee objects currently in memory
    private String filePath; // Path to the file where employee data is stored

    // Constructor to initialize employeeList and filePath
    public New_Employee(List<Employee> employeeList, String filePath) {
        this.employeeList = employeeList; // Assigns the provided employee list to the class field for in-memory storage
        this.filePath = filePath; // Sets the file path to where employees are stored, enabling file read/write operations
    }

    // Method to add a new employee, prompting for user input and saving to file
    public void add_New_Employee(Scanner scanner) throws IOException {
        System.out.println("Enter Employee Name:");
        String name = scanner.nextLine().trim(); // Reads and trims the employee name entered by the user

        // Check if the employee already exists in the file to avoid duplicates
        if (is_Employee_In_File(name)) {
            System.out.println("Employee with name \"" + name + "\" already exists."); // Notifies the user if the employee is already in the file
            return; // Exits the method to prevent duplicate entries
        }

        // Select Role Level by calling the helper method that prompts for a role
        Role_Level roleLevel = select_Role_Level(scanner);

        // Select Department by calling the helper method that prompts for a department
        Department_Name department = select_Department_Name(scanner);

        // Create and add the new employee with the specified name, role, and department
        Employee newEmployee = new Employee(name, roleLevel, department); // Creates a new Employee object with the provided details
        employeeList.add(newEmployee); // Adds the new Employee to the in-memory list
        save_Employee_To_File(newEmployee); // Saves the new employee to the file, ensuring data persistence

        // Confirms that the new employee has been added successfully
        System.out.println(name + " has been added as " + roleLevel + " to " + department + " successfully!");
    }

    // Method to select a Role Level with validation to ensure a correct choice is made
    private Role_Level select_Role_Level(Scanner scanner) {
        int roleChoice = 0; // Holds the user's choice for role level
        boolean validInput = false; // Tracks if a valid input has been provided

        while (!validInput) { // Loop continues until a valid role level is chosen
            System.out.println("Select Role:");
            for (int i = 0; i < Role_Level.values().length; i++) { // Iterates through all Role_Level enum values
                System.out.println((i + 1) + ". " + Role_Level.values()[i]); // Displays each role level with an index for user selection
            }

            if (scanner.hasNextInt()) { // Checks if the user input is an integer
                roleChoice = scanner.nextInt();
                scanner.nextLine(); // Consumes the newline character left by nextInt
                if (roleChoice >= 1 && roleChoice <= Role_Level.values().length) { // Validates input to ensure it's within range
                    validInput = true; // Marks input as valid to exit the loop
                } else {
                    System.out.println("Invalid choice, please select a number between 1 and " + Role_Level.values().length + "."); // Prompts for correct input if out of range
                }
            } else {
                System.out.println("Invalid input, please enter an integer."); // Handles non-integer inputs
                scanner.nextLine(); // Clears invalid input
            }
        }

        return Role_Level.values()[roleChoice - 1]; // Returns the chosen Role_Level based on the user's input
    }

    // Method to select a Department with validation to ensure a correct choice is made
    private Department_Name select_Department_Name(Scanner scanner) {
        int deptChoice = 0; // Holds the user's choice for department
        boolean validInput = false; // Tracks if a valid input has been provided

        while (!validInput) { // Loop continues until a valid department is chosen
            System.out.println("Select Department:");
            for (int i = 0; i < Department_Name.values().length; i++) { // Iterates through all Department_Name enum values
                System.out.println((i + 1) + ". " + Department_Name.values()[i]); // Displays each department with an index for user selection
            }

            if (scanner.hasNextInt()) { // Checks if the user input is an integer
                deptChoice = scanner.nextInt();
                scanner.nextLine(); // Consumes the newline character left by nextInt
                if (deptChoice >= 1 && deptChoice <= Department_Name.values().length) { // Validates input to ensure it's within range
                    validInput = true; // Marks input as valid to exit the loop
                } else {
                    System.out.println("Invalid choice, please select a number between 1 and " + Department_Name.values().length + "."); // Prompts for correct input if out of range
                }
            } else {
                System.out.println("Invalid input, please enter an integer."); // Handles non-integer inputs
                scanner.nextLine(); // Clears invalid input
            }
        }

        return Department_Name.values()[deptChoice - 1]; // Returns the chosen Department_Name based on the user's input
    }

    // Method to check if an employee with the specified name already exists in the file
    public boolean is_Employee_In_File(String name) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { // Opens a BufferedReader to read the file line-by-line
            String line;
            while ((line = reader.readLine()) != null) { // Reads each line of the file until the end
                if (line.startsWith("Employee: " + name + ",")) { // Checks if the line starts with the given employee's name
                    return true; // Returns true if an employee with the name is found, indicating a duplicate
                }
            }
        }
        return false; // Returns false if no matching employee name is found, allowing a new entry
    }

    // Method to save a new employee to the file, appending the entry to avoid overwriting existing data
    private void save_Employee_To_File(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Opens a BufferedWriter in append mode to add to the file
            writer.write("Employee: " + employee.getName() + ", Role: " + employee.getRole() + ", Department: " + employee.getDepartment()); // Writes the employee's details in a formatted string
            writer.newLine(); // Adds a newline after the entry for readability
        } catch (IOException e) { // Catches any IOException that might occur during file writing
            System.out.println("An error occurred while saving the employee to the file: " + e.getMessage()); // Prints an error message if writing fails
        }
    }
}