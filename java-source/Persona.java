/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr_manager_test;

/**
 *
 * @author rafae
 */
public class Persona {
    protected String name; // A protected field for the name, accessible by subclasses of Persona

    // Constructor: Initializes the name field when a Persona object (or subclass) is created
    public Persona(String name) {
        this.name = name; // Assigns the provided name to the name field
    }

    // Getter method for name: Provides access to the name field for subclasses and other classes
    public String getName() {
        return name;
    }

    // Setter method for name: Allows updating the name field, accessible to subclasses and other classes
    public void setName(String name) {
        this.name = name;
    }

    // Override of the toString method from Object class: Provides a string representation of the object
    @Override
    public String toString() {
        return "Name: " + name; // Returns a formatted string with the name, useful for displaying or logging the object
    }
}
