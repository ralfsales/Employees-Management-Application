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
import java.util.Random;

/**
 *
 * @author rafae
 */
public class Random_Employee_Generator {
    private List<Employee> employeeList;
    private String filePath;
    
    // Separate list of first names 
    private static final String[] FIRST_NAMES = {
        "John", "Jane", "Alice", "Joao", "Maria", "Jose", "Ana", "Antonio", 
        "Paulo", "Lucas", "Gabriela", "Bruno", "Fernanda", "Rafael", 
        "Mariana", "Pedro", "Camila", "Ricardo", "Daniela", "Carlos", 
        "Julia", "Andre", "Bianca","Liam","Aoife","Conor","Niamh","Ronan",
        "Michael", "Sarah", "Emma", "Patrick", "Sean", "Caoimhe", "Ciara",
        "Eabha", "Orla", "Finn", "Tadhg", "Eoin", "Grainne", "Cillian", "Darragh",
        "Aisling", "Fiona", "Maeve", "Cathal", "Molly", "Aidan", "Declan",
        "Sophie", "Clara", "Ethan", "Ava", "Oscar", "Siobhan", "Tomas",
        "Fergus", "Nora", "Mairead", "Saoirse", "Eimear", "Oisin",
        "Seamus", "Moira", "Brian", "Siobhan", "Malachy", "Evelyn", "Lorcan",
        "Niall", "Clodagh", "Grady", "Sinead", "Eilis", "Cormac", "Roisin", "Padraig",
        "Adelaide", "Klaus", "Lena", "Hans", "Matthias", "Greta", "Friedrich", "Lukas",
        "Sabine", "Anneliese", "Jurgen", "Heidi", "Stefan", "Gisela", "Wolfgang", "Ingrid",
        "Werner", "Helga", "Rolf", "Ursula"

    };
    
    // Separate list of last names
    private static final String[] SURNAMES = {
        "Doe", "Smith", "Johnson", "Silva", "Murphy", "Santos", "Pereira", 
        "Souza", "Rodrigues", "Almeida", "McCarthy ", "Barbosa", "Lima", 
        "O’Brien", "Gomes", "Ramos", "Nascimento", "Martins", 
        "Moreira", "Kelly", "Carvalho", "Correia", "Rocha","O’Sullivan",
        "Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker",
        "Hoffmann", "Schafer", "Koch", "Bauer", "Richter", "Klein", "Wolf", "Schroder",
        "Neumann", "Braun", "Zimmermann", "Schwarz", "Clark", "Lewis", "Walker", "Hall", "Allen", "Young", "King", "Wright",
        "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", 
        "Mitchell", "Perez", "Roberts", "Turner", "Phillips",
        "Garcia", "Fernandez", "Martinez", "Lopez", "Romero", "Marino", "Rossi", "Russo",
        "Esposito", "Bianchi", "Ferrari", "Serrano", "Gallo", "Costa", "Santana",
        "Castillo", "Torres", "Ortega", "Ricci", "Morales"

    };

    public Random_Employee_Generator(List<Employee> employeeList, String filePath) {
        this.employeeList = employeeList; // Assigns the provided employee list to the class's employeeList field for in-memory data storage
        this.filePath = filePath; // Assigns the provided file path to the class's filePath field, allowing access to employee data storage or retrieval
    }


    public void generate_Random_Employee() {
        Random random = new Random(); // Create a new random object
        String name; // Create a String variable to hold the generated name

        // Keep generating a unique name by combining a random first name and surname
        do {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]; // Gets the first name from the respective list
            String lastName = SURNAMES[random.nextInt(SURNAMES.length)]; // Gets the last name from the respective list
            name = firstName + " " + lastName; // Create the name variable by combining firstname and lastname randomly generated
        } while (is_Employee_In_File(name)); // The loop for generating a random name only stops if this new name is unick and not existing in the current list

        // Generate random ManagerType and DepartmentName for the new employee
        Role_Level roleLevel = Role_Level.values()[random.nextInt(Role_Level.values().length)]; 
        // Selects a random role level from the Role_Level enum by generating a random index within the range of Role_Level values

        Department_Name department = Department_Name.values()[random.nextInt(Department_Name.values().length)]; 
        // Selects a random department from the Department_Name enum by generating a random index within the range of Department_Name values

        // Create and add the unique employee
        Employee newEmployee = new Employee(name, roleLevel, department); 
        // Instantiates a new Employee object with a randomly generated role level and department, along with a specified name

        employeeList.add(newEmployee); 
        // Adds the newly created Employee object to the in-memory list (employeeList) to keep track of all employees

        save_Employee_To_File(newEmployee); 
        // Saves the new employee to a file, ensuring data persistence for future access

        System.out.println("Generated: " + newEmployee); 
        // Prints the details of the generated employee to confirm the creation and display the randomly assigned attributes

    }
    
     // Method to check if an employee with the given name exists in the file. Returns true if found, false otherwise.
    private boolean is_Employee_In_File(String name) { 

        // Creates a BufferedReader to read from the file specified by filePath.
        // Uses try-with-resources to automatically close the reader after use, even if an exception occurs.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { 
            // Creates a BufferedReader to read from the file specified by filePath.
            // Uses try-with-resources to automatically close the reader after use, even if an exception occurs.

            String line; // Holds each line of the file as it’s read

            while ((line = reader.readLine()) != null) { 
                // Loops through each line of the file until the end (when readLine() returns null).

                if (line.startsWith("Employee: " + name + ",")) { 
                    // Checks if the current line starts with "Employee: " followed by the given name and a comma.
                    // This format is expected based on the assumption that employee entries in the file start with "Employee: [name],"

                    return true; // Returns true immediately if a matching line (i.e., matching name) is found
                }
            }
        } catch (IOException e) { 
            // Catches any IOException that may occur during file reading (e.g., file not found, read error)

            System.out.println("An error occurred while checking the file: " + e.getMessage()); 
            // Prints an error message to indicate the issue with file access, helping in debugging and user feedback
        }

        return false; // Returns false if no matching name was found after reading the entire file
    }


    private void save_Employee_To_File(Employee employee) { 
        // Method to save a single Employee object to the file. Appends the employee's details to the file.

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { 
            // Opens the file specified by filePath in append mode using a BufferedWriter.
            // The 'true' argument in FileWriter enables append mode, so existing data is retained in the file.
            // Try-with-resources is used here to automatically close the writer after use.

            writer.write("Employee: " + employee.getName() + ", Role: " + employee.getRole() + ", Department: " + employee.getDepartment()); 
            // Writes the employee's details in a formatted string to the file.
            // This includes the name, role, and department in a structured format for easy readability and parsing.

            writer.newLine(); 
            // Adds a newline character after writing the employee's details, so each employee entry starts on a new line.

        } catch (IOException e) { 
            // Catches any IOException that may occur during file operations, such as file write errors or access issues.

            System.out.println("An error occurred while writing to the file: " + e.getMessage()); 
            // Prints an error message indicating that a file write error occurred, along with the specific exception message.
            // This provides helpful feedback for debugging issues related to file access or permissions.
        }
    }

}
