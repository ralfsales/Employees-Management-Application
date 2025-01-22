/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import hr_manager_test.HR_manager_test.Department_Name;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafae
 */

public class Department {
    // Field to store the name of the department, using the Department_Name enum type
    private Department_Name departmentName;

    // A list to hold Employee objects associated with this department
    private List<Employee> employees;

    // Constructor that initializes the department with a specified name
    // and creates an empty list for employees
    public Department(Department_Name departmentName) {
        this.departmentName = departmentName; // Assigns the provided department name to the departmentName field
        this.employees = new ArrayList<>(); // Initializes an empty ArrayList to hold employees in this department
    }

    // Getter method for departmentName field
    // Allows external access to retrieve the department name
    public Department_Name getDepartmentName() {
        return departmentName;
    }

    // Method to add an Employee to this department's employee list
    // Takes an Employee object as a parameter
    public void addEmployee(Employee employee) {
        employees.add(employee); // Adds the employee to the employees list for this department
    }

    // Getter method for employees field
    // Returns the list of employees in this department
    public List<Employee> getEmployees() {
        return employees;
    }

    // Overrides the toString method to provide a readable string representation of the department
    // Returns the department name and the number of employees in this department
    @Override
    public String toString() {
        return "Department: " + departmentName + ", Employees: " + employees.size();
    }
}