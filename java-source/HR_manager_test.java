/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hr_manager_test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rafae
 */

/*
 HR_manager_test is created as the main entry point for the HR Management application,
 providing a console-based interface to interact with various functionalities 
 for managing employees within a company.
 */
public class HR_manager_test {
    
    /*
    This enum lists the main menu options for the program.
    Each option corresponds to a unique operation as named,
    which the user can choose from the menu. This enum simplifies managing and updating the menu items
    and ensures that each option has a unique identifier.
    */
    enum Menu_Options {
    NEW_EMPLOYEE, GENERATE_RANDOM_EMPLOYEE, SORT_EMPLOYEES, SEARCH_EMPLOYEE, EDIT_EMPLOYEE,
    REMOVE_EMPLOYEE, VIEW_BY_DEPARTMENT, VIEW_BY_ROLE, EXIT
    }

    /*
    The Role_Level enum defines various roles within the company.
    These roles help categorize employees and can influence permissions and responsibilities within the application.
    For instance, these roles could determine which department an employee is eligible to manage or work in.
    */
    enum Role_Level {
    MANAGER, SUPERVISOR, TECNICIAN, ASSISTANT, INTERN, APPRENTICE
    }

    /*
    This enum specifies different departments in the company.
    Each employee is associated with one of these departments.
    The department information can be used to filter employees,
    view department-specific lists, and organize data within the application.
    */
    enum Department_Name {
    HR, SALES, DEVELOPMENT, PROJECTS
    }

    public static void main(String[] args) throws IOException {
        
        List<Employee> employeeList = new ArrayList<>(); // Create an empty list to hold Employee objects.
        Scanner scanner = new Scanner(System.in); // And initialize a Scanner to capture user input.

        System.out.println("Hey, welcome to HR Managent Application, I am going to assist you from now 🙋🏼‍");
        String filePath; // Initiate variable filePath
        File file; // Initiate variable file

        /*
        Use while-loop to ask user for the file path.
        It repeats until a valid file is provided. This file will be used to load or save employee data.
        */
        while (true) {
            System.out.print("Please enter the name or path of the file that you want to work on: ");
            filePath = scanner.nextLine(); // Retrive user input as the file path or name
            file = new File(filePath);

            // Check if the file exists and is readable
            if (file.exists() && file.isFile() && file.canRead()) {
                System.out.println("File found and ready to be used.");
                break; // Exit the loop if the file is valid
            } else {
                System.out.println("File not found or cannot be read. Please enter a valid file name or path.");
            }
        }
        /*
        Calls load_Employees_From_File to read existing employee data from the file and store it in employeeList.
        showEmployees is then called to display the first 20 employees loaded from the file.
        */ 
        employeeList = load_Employees_From_File(filePath);
        System.out.println("These are the current first 20 Employees from the file:");
        showEmployees(employeeList);
        
        // Instantiates the different classes used to manage various aspects of employee data.
        New_Employee creator = new New_Employee(employeeList, filePath);
        Random_Employee_Generator generator = new Random_Employee_Generator(employeeList, filePath);
        Sorter_Employee sorter = new Sorter_Employee(filePath);
        Searcher_Employee searcher = new Searcher_Employee(filePath);
        Editor_Employee editor = new Editor_Employee(employeeList, filePath);
        Remover_Employee remover = new Remover_Employee(filePath);
        Filter_Employee_By_Department viewerByDepartment = new Filter_Employee_By_Department(filePath);
        Filter_Employee_By_Role_Level viewerByRole = new Filter_Employee_By_Role_Level(filePath);

        // Starts a loop that repeatedly displays the menu and allows the user to select actions until they choose to exit.
        boolean key = true;
        while (key) {
        System.out.println("\nSelect an option:");
        System.out.println("1. + Add Employee");
        System.out.println("2. ⨝ Generate a Random Employee");
        System.out.println("3. ⇅ Sort Employees");
        System.out.println("4. 🔎 Search for an Employee");
        System.out.println("5. 📝 Edit a Specific Employee");
        System.out.println("6. 🗑 Remove an Employee");
        System.out.println("7. 📃 View Employees List by Department");
        System.out.println("8. 🗃 View Employees List by Role");
        System.out.println("9. 📴 Exit application");

        int choice = 0;
        boolean validInput = false;
        // Loop until we get a valid integer input within the range
        while (!validInput) {
            System.out.print("Enter your choice (1-9): ");
            if (scanner.hasNextInt()) { // Validating if user input is a Integer 
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 9) { // Validating if user input is corrent in the solicited range of numbers
                    validInput = true; // Input is valid otherwise the system will prompt a error messege and continue asking user for a correct input
                } else {
                    System.out.println("Invalid choice, please select a number between 1 and 9."); // Informing user to insert a valid number
                }
            } else {
                System.out.println("Invalid input, please enter an integer."); // Informing user to insert a valid input
                scanner.nextLine(); // Clear invalid input
            }
        }

        switch (choice) {
            case 1:
                creator.add_New_Employee(scanner);
                break;
            case 2:
                generator.generate_Random_Employee();
                break;
            case 3:
                sorter.sort_Employees();
                break;
            case 4:
                System.out.println("Enter Employee Name to Search:");
                String nameToSearch = scanner.nextLine();
                searcher.search_Employee(nameToSearch);
                break;
            case 5:
                editor.edit_Employee(scanner);
                break;
            case 6:
                remover.remove_Employee(scanner);
                break;
            case 7:
                viewerByDepartment.employees_By_Department(scanner);
                break;
            case 8:
                viewerByRole.Employees_By_Role_Level(scanner);
                break;
            case 9:
                System.out.println("Too-de-loo 🙋🏼‍"); // This is the only option that exit the program
                key = false;
                break;
            default:
                System.out.println("Invalid choice, please select a number between 1 and 9.");
        }
    }
        scanner.close();
    }
    
    // Create a method to read a file given by user
    public static List<Employee> load_Employees_From_File(String filePath) {
    // Initialise an empty list to store Employee objects
    List<Employee> employees = new ArrayList<>();
    
    // Try to open the file at the specified filePath for reading
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line; // Initialise a variable to hold each line read from the file
        // Loop through each line in the file until there are no more lines to read
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", "); // Split the line by ", " to separate the employee`s detais into an array
            // Check if the array has exactly 3 parts (name, role, department)
            if (parts.length == 3) {
                String name = parts[0].replace("Employee: ", "").trim(); // Extract and format the name from the first part by removing the label "Employee: "
                Role_Level managerType = Role_Level.valueOf(parts[1].replace("Role: ", "").trim().toUpperCase()); // Extract and format the role by removing the label "Role: " and converting it to uppercase
                Department_Name department = Department_Name.valueOf(parts[2].replace("Department: ", "").trim().toUpperCase()); // Extract and format the department by removing the label "Department: " and converting it to uppercase
                employees.add(new Employee(name, managerType, department)); // Create a new Employee object with the extracted name, role, and department
            }
        }
    } catch (IOException e) {
        System.out.println("An error occurred while loading employees from the file: " + e.getMessage());
    }
    return employees;
    }
    
    private static void showEmployees(List<Employee> employees) {
        // Determine the number of employees to display.
        // The Math.min function is used to limit the number of employees shown to 20 or less.
        // If the employees list contains fewer than 20 items, the limit will be the size of the list.
        // This ensures that the program doesn't attempt to access elements beyond the end of the list,
        // which would cause an IndexOutOfBoundsException.
        int limit = Math.min(20, employees.size());

        // Loop through the employees list and display each employee's details up to the defined limit.
        // The for-loop starts from index 0 and iterates up to (limit - 1), ensuring only 'limit' number of
        // employees are displayed.
        for (int i = 0; i < limit; i++) {
            // Print each employee's information to the console in a user-friendly format.
            // (i + 1) provides a numbered list starting from 1, which is more readable than a zero-based index.
            // Concatenation of (i + 1) + ". " gives a numbered prefix, for example, "1. " or "2. ".
            // employees.get(i) retrieves the Employee object at index 'i' from the list.
            // The Employee object’s toString() method is implicitly called when using System.out.println,
            // meaning that each employee's information will be displayed according to the toString() implementation.
            // If Employee’s toString() method has been overridden, it might display relevant details like
            // the employee's name, department, role, etc.
            System.out.println((i + 1) + ". " + employees.get(i));
        }
    }
}