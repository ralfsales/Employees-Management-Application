/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import hr_manager_test.HR_manager_test.Department_Name;
import hr_manager_test.HR_manager_test.Role_Level;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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

// Create a Class to handle the removal of an employee from a file containing employee data.
public class Remover_Employee {
    private String filePath; // Stores the path to the file where employee data is stored.
                            // This path is used to locate and modify the file when an employee needs to be removed.

    // Constructor: Initializes the filePath field with the specified path
    public Remover_Employee(String filePath) {
        this.filePath = filePath; // Sets the provided file path to the class’s filePath field,
        // allowing other methods within this class to access the specified file for employee removal operations.
    }
    
    // Method to load a list of Employee objects from a file. Returns an empty list if any error occurs.
    public List<Employee> load_Employees_From_File() { 

        List<Employee> employees = new ArrayList<>(); 
        // Initializes an empty list to store Employee objects as they are loaded from the file.

        File file = new File(filePath); 
        // Creates a File object representing the file at the specified filePath.

        // Check if the file exists and is readable
        if (!file.exists()) { 
            // Checks if the file exists at the specified path.

            System.out.println("Error: The file at the specified path does not exist: " + filePath); 
            // Outputs an error message if the file does not exist, providing feedback about the missing file.

            return employees; 
            // Returns the empty list since there is no file to read from.
        }
        if (!file.canRead()) { 
            // Checks if the file is readable (e.g., no permissions issue).

            System.out.println("Error: The file at the specified path cannot be read: " + filePath); 
            // Outputs an error message if the file cannot be read.

            return employees; 
            // Returns the empty list since the file cannot be accessed.
        }

        // Attempt to load the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { 
            // Opens a BufferedReader to read the file line-by-line.
            // Try-with-resources is used to ensure the reader is closed automatically after use.

            String line; 
            // Variable to store each line of the file as it’s read.

            while ((line = reader.readLine()) != null) { 
                // Reads each line of the file until the end of the file (when readLine() returns null).

                String[] parts = line.split(", "); 
                // Splits each line by ", " into an array to separate name, role, and department.

                if (parts.length == 3) { 
                    // Checks if the line has exactly three parts (name, role, department) to ensure proper format.

                    String name = parts[0].replace("Employee: ", "").trim(); 
                    // Extracts and cleans up the employee name by removing the "Employee: " prefix and any whitespace.

                    Role_Level managerType = Role_Level.valueOf(parts[1].replace("Role: ", "").trim().toUpperCase()); 
                    // Extracts the role, removes the "Role: " prefix, trims whitespace, and converts to uppercase.
                    // Uses Role_Level.valueOf() to convert the string to the Role_Level enum, handling valid roles only.

                    Department_Name department = Department_Name.valueOf(parts[2].replace("Department: ", "").trim().toUpperCase()); 
                    // Extracts the department, removes the "Department: " prefix, trims whitespace, and converts to uppercase.
                    // Uses Department_Name.valueOf() to convert the string to the Department_Name enum, handling valid departments only.

                    employees.add(new Employee(name, managerType, department)); 
                    // Creates a new Employee object with the extracted data and adds it to the employees list.
                } else { 
                    // If the line does not have exactly three parts, it is improperly formatted.

                    System.out.println("Warning: Skipping improperly formatted line: " + line); 
                    // Warns the user and skips the line without crashing the program.
                }
            }
            System.out.println("Successfully loaded " + employees.size() + " employees from file."); 
            // Prints a success message, including the number of employees loaded, for user feedback.

        } catch (FileNotFoundException e) { 
            // Catches an exception if the file is not found (redundant here because we already checked file existence).

            System.out.println("Error: File not found at path: " + filePath); 
            // Provides an error message for a missing file.

        } catch (IOException e) { 
            // Catches any other I/O exceptions that may occur during file reading.

            System.out.println("Error: An I/O error occurred while reading the file: " + e.getMessage()); 
            // Outputs an error message with details of the I/O issue, helping with troubleshooting.

        } catch (IllegalArgumentException e) { 
            // Catches exceptions if the role or department names in the file do not match the expected enum values.

            System.out.println("Error: File contains invalid manager type or department name. Please check file format."); 
            // Prints an error message to indicate that the file contains invalid data.

        }

        return employees; 
        // Returns the list of employees, which may be empty if errors occurred during loading.
    }


    // Method to remove an employee from the list and update the file accordingly.
    public void remove_Employee(Scanner scanner) { 

        List<Employee> employeeList = load_Employees_From_File(); 
        // Loads the current list of employees from the file, ensuring we have the latest data.
        // This step is essential to ensure we’re working with up-to-date information when removing an employee.

        System.out.println("Enter the name of the employee to remove:");
        String nameToRemove = scanner.nextLine(); 
        // Prompts the user to enter the name of the employee they want to remove.
        // This name is stored in the `nameToRemove` variable after trimming any extra whitespace.

        // Find the employee by name
        Optional<Employee> employeeOpt = employeeList.stream()
                .filter(emp -> emp.getName().equalsIgnoreCase(nameToRemove.trim())) 
                // Filters the employee list for a match with the given name, ignoring case sensitivity.
                .findFirst(); 
                // Retrieves the first match found, if any, wrapped in an Optional to handle cases where no match is found.

        if (employeeOpt.isPresent()) { 
            // Checks if an employee with the specified name exists in the list.

            Employee employee = employeeOpt.get(); 
            // Retrieves the matched employee from the Optional.

            employeeList.remove(employee); 
            // Removes the found employee from the in-memory list.

            save_Employees_To_File(employeeList); 
            // Saves the updated employee list back to the file, ensuring the removed employee is no longer present in storage.

            System.out.println("Employee " + employee.getName() + " has been removed successfully."); 
            // Confirms the removal to the user, displaying the name of the removed employee for clarity.

        } else { 
            // If no matching employee is found in the list,

            System.out.println("Employee not found."); 
            // Informs the user that the specified employee was not found, providing feedback to avoid confusion.
        }
    }

    // Method to save a list of Employee objects to the file. This overwrites the file with the updated list.
    private void save_Employees_To_File(List<Employee> employees) { 

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { 
            // Opens the file specified by filePath in write mode using a BufferedWriter.
            // Write mode (without the 'true' argument) overwrites any existing content, so the file only contains the provided list.
            // Try-with-resources is used here to automatically close the writer after writing, ensuring proper resource management.

            for (Employee employee : employees) { 
                // Iterates over each Employee object in the provided list.

                writer.write("Employee: " + employee.getName() + ", Role: " + employee.getRole() + ", Department: " + employee.getDepartment()); 
                // Writes each employee's details to the file in a structured format: "Employee: [name], Role: [role], Department: [department]"
                // This makes each entry readable and easy to parse later.

                writer.newLine(); 
                // Adds a newline after each employee entry, ensuring each record starts on a new line in the file for readability.
            }

            System.out.println("File updated successfully."); 
            // Confirms to the user that the file was updated without issues, providing helpful feedback after a successful save.

        } catch (IOException e) { 
            // Catches any IOException that may occur during file operations, such as write errors or access issues.

            System.out.println("An error occurred while saving employees to the file: " + e.getMessage()); 
            // Prints an error message indicating a problem with saving the employees, along with the exception message.
            // This message helps with debugging by providing details about the file access or writing issue.
        }
    }

}