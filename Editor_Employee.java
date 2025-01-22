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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author rafae
 */
public class Editor_Employee {
    private List<Employee> employeeList; // Holds a list of employees objects
    private String filePath; // Path to the file where employee data is stored

    // Constructor: Initializes the employee list and file path for loading/saving employee data
    public Editor_Employee(List<Employee> employeeList, String filePath) {
        this.employeeList = employeeList;
        this.filePath = filePath;
    }

    // edit_Employe method allows editing an employee's details based on user input
    public void edit_Employee(Scanner scanner) {
        List<Employee> employeeList = load_Employees_From_File(filePath); // Reloads employee data from the file to ensure edits are applied to the latest version

        System.out.println("Enter the name and surname of the employee to edit e.i: Ruan Alves -> ");
        String nameToEdit = scanner.nextLine(); // Captures the employee's name from user input to find the specific employee to edit

        // Searches for the employee by name in the list
        Optional<Employee> employeeOpt = employeeList.stream()
                .filter(emp -> emp.getName().equalsIgnoreCase(nameToEdit.trim())) // Filters by name, ignoring case and trimming extra whitespace
                .findFirst(); // Finds the first employee that matches the name entered by the user

        if (employeeOpt.isPresent()) { // Checks if an employee with the specified name was found
            Employee employee = employeeOpt.get(); // Retrieves the found employee object
            System.out.println("Editing details for: " + employee); // Displays the current details of the employee being edited

            // Prompt to update the employee's name with input validation
            int changeName = 0;
            boolean validNameInput = false;

            while (!validNameInput) { // Loops until a valid choice (1 or 2) is made
                System.out.println("Do you want to change the employee's name? (1 for Yes, 2 for No):");
                if (scanner.hasNextInt()) { // Checks if the input is an integer
                    changeName = scanner.nextInt();
                    scanner.nextLine(); // Consumes newline after integer input
                    if (changeName == 1 || changeName == 2) {
                        validNameInput = true; // Validates input by accepting only 1 or 2
                    } else {
                        System.out.println("Invalid choice, please enter 1 for Yes or 2 for No."); // Prompts user for correct input if invalid
                    }
                } else {
                    System.out.println("Invalid input, please enter an integer (1 for Yes or 2 for No)."); // Handles non-integer inputs
                    scanner.nextLine(); // Clears invalid input
                }
            }

            // Updates employee's name if the user chooses to
            if (changeName == 1) {
                System.out.println("Enter employee's new name: ");
                String newName = scanner.nextLine().trim(); // Captures and trims the new name from user input
                employee.setName(newName); // Sets the new name using the employee's setName() method
            } else {
                System.out.println("Employee's name is kept the same"); // Confirms that the name remains unchanged if chosen
            }

            // Selects new Role Level with input validation
            int roleChoice = 0;
            boolean validRoleInput = false;

            while (!validRoleInput) { // Loops until a valid role choice (1, 2, or 3) is made
                System.out.println("Select new Role Level:");
                for (int i = 0; i < Role_Level.values().length; i++) { // Iterates through available Role_Level enum values
                    System.out.println((i + 1) + ". " + Role_Level.values()[i]); // Displays each role level option with an index for user selection
                }

                if (scanner.hasNextInt()) { // Checks if input is an integer
                    roleChoice = scanner.nextInt();
                    scanner.nextLine(); // Consumes newline
                    if (roleChoice >= 1 && roleChoice <= Role_Level.values().length) {
                        validRoleInput = true; // Validates input by ensuring it's within the valid range of roles
                    } else {
                        System.out.println("Invalid choice, please select a number between 1 and " + Role_Level.values().length + "."); // Prompts user for correct input if invalid
                    }
                } else {
                    System.out.println("Invalid input, please enter an integer (1 to " + Role_Level.values().length + ")."); // Handles non-integer inputs
                    scanner.nextLine();
                }
            }

            Role_Level newRoleLevel = Role_Level.values()[roleChoice - 1]; // Gets the selected role level based on the user's input
            employee.setRole(newRoleLevel); // Updates the employee's role with the new selection

            // Selects new Department with input validation
            int deptChoice = 0;
            boolean validDeptInput = false;

            while (!validDeptInput) { // Loops until a valid department choice (1 to number of departments) is made
                System.out.println("Select new Department:");
                for (int i = 0; i < Department_Name.values().length; i++) { // Iterates through available Department_Name enum values
                    System.out.println((i + 1) + ". " + Department_Name.values()[i]); // Displays each department option with an index for user selection
                }

                if (scanner.hasNextInt()) { // Checks if input is an integer
                    deptChoice = scanner.nextInt();
                    scanner.nextLine(); // Consumes newline
                    if (deptChoice >= 1 && deptChoice <= Department_Name.values().length) {
                        validDeptInput = true; // Validates input by ensuring it's within the valid range of departments
                    } else {
                        System.out.println("Invalid choice, please select a number between 1 and " + Department_Name.values().length + "."); // Prompts user for correct input if invalid
                    }
                } else {
                    System.out.println("Invalid input, please enter an integer (1 to " + Department_Name.values().length + ")."); // Handles non-integer inputs
                    scanner.nextLine();
                }
            }

            Department_Name newDepartment = Department_Name.values()[deptChoice - 1]; // Gets the selected department based on the user's input
            employee.setDepartment(newDepartment); // Updates the employee's department with the new selection

            // Saves the modified employee list back to the file
            save_Employees_To_File(employeeList); // Calls the method to save updated employee data to file for data persistence
            System.out.println("Employee updated: " + employee); // Confirms that the employee's information has been updated successfully
        } else {
            System.out.println("Employee not found."); // Informs the user if no employee matches the name provided
        }
    }


    // load_Employees_From_File: Reads employee data from a file and returns a list of employees
    public List<Employee> load_Employees_From_File(String filePath1) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { // Reads data line by line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) { // Assumes each line contains 3 comma-separated parts
                    String name = parts[0].replace("Employee: ", "").trim();
                    Role_Level role = Role_Level.valueOf(parts[1].replace("Role: ", "").trim().toUpperCase());
                    Department_Name department = Department_Name.valueOf(parts[2].replace("Department: ", "").trim().toUpperCase());
                    employees.add(new Employee(name, role, department));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading employees from the file: " + e.getMessage());
        }
        return employees;
    }

    // save_Employees_To_File: Writes the list of employees to a file, overwriting previous data
    private void save_Employees_To_File(List<Employee> employees) {
        // Opens a BufferedWriter with a FileWriter for the specified file path in write mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Loops through each employee in the provided list of employees
            for (Employee employee : employees) {
                // Writes a formatted string containing employee details: name, role, and department
                writer.write("Employee: " + employee.getName() + ", Role: " + employee.getRole() + ", Department: " + employee.getDepartment());
                writer.newLine(); // Adds a newline after each employee's data for proper file formatting
            }
        } catch (IOException e) { // Catches any IOException that occurs during file operations
            // Prints an error message if an IOException occurs, indicating a problem with file access or writing
            System.out.println("An error occurred while saving employees to the file: " + e.getMessage());
        }
    }

}
