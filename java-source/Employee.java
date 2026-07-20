/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

import hr_manager_test.HR_manager_test.Department_Name;
import hr_manager_test.HR_manager_test.Role_Level;

/**
 *
 * @author rafae
 */
public class Employee extends Persona { // Defines the Employee class as a subclass of Persona, inheriting its properties and methods
    private Role_Level role; // Declares a private field to store the employee's role as an enum type Role_Level
    private Department_Name department; // Declares a private field to store the employee's department as an enum type Department_Name

    // Constructor: Initializes the Employee object with name, role, and department
    public Employee(String name, Role_Level role, Department_Name department) {
        super(name); // Calls the superclass (Persona) constructor to set the employee's name
        this.role = role; // Assigns the role parameter to the role field of the Employee
        this.department = department; // Assigns the department parameter to the department field of the Employee
    }

    // Getter for role: Returns the employee's role
    public Role_Level getRole() {
        return role;
    }

    // Setter for role: Sets the employee's role to a new value
    public void setRole(Role_Level role) {
        this.role = role;
    }

    // Getter for department: Returns the employee's department
    public Department_Name getDepartment() {
        return department;
    }

    // Setter for department: Sets the employee's department to a new value
    public void setDepartment(Department_Name department) {
        this.department = department;
    }

    // toString method: Provides a string representation of the Employee object
    @Override
    public String toString() {
        return super.toString() + ", Role: " + role + ", Department: " + department; // Calls Persona's toString and appends role and department details
    }
}
