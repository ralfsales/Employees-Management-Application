/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author rafae
 */

// Create a Class for searching an employee in a file containing employee data.
public class Searcher_Employee { 
    

    private String filePath; 
    // Stores the path to the file where employee data is located.
    // This field allows the search method to know which file to search for employee information.

    // Constructor: Initializes the filePath field with the provided file path
    public Searcher_Employee(String filePath) { 
        this.filePath = filePath; 
        // Sets the file path for this instance, enabling it to access and search the specified file.
    }

    // Method to search for an employee by name within the file
    public void search_Employee(String nameToSearch) { 
        // Takes a name as a parameter and searches for matching records in the file.

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { 
            // Opens the file specified by filePath in read mode, using BufferedReader for efficient line-by-line reading.
            // Try-with-resources is used to automatically close the reader after use, ensuring resource management.

            String line; // Stores each line read from the file
            while ((line = reader.readLine()) != null) { 
                // Reads each line from the file until the end (when readLine() returns null).

                if (line.toLowerCase().contains(nameToSearch.toLowerCase())) { 
                    // Converts both the line and the search term to lowercase to perform a case-insensitive search.
                    // Checks if the current line contains the search term, indicating a potential match.

                    System.out.println("Found: " + line); 
                    // Prints the matching line, which contains the details of the found employee.

                    return; 
                    // Exits the method immediately after finding the first match, as the search is complete.
                }
            }

            System.out.println("Employee not found."); 
            // If the loop completes without finding a match, it informs the user that the employee wasn’t found.

        } catch (IOException e) { 
            // Catches any I/O exceptions that may occur while opening or reading the file, such as file not found or read errors.

            System.out.println("An error occurred while searching the file: " + e.getMessage()); 
            // Prints an error message to provide feedback on the issue, helping with debugging.
        }
    }
}

