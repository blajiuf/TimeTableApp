/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetableapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import helpers.RandomString;

/**
 *
 * @author filip
 */
public final class Student {
    
    private String firstName;
    private String lastName;
    private int age;
    private String uniqueID;
    private int id;
    
//    Constructors
    
    public Student(String firstName, String lastName){
        this.uniqueID = new RandomString().nextString();
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Student(String uniqueID){
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student WHERE uniqueID = '" + uniqueID + "' LIMIT 1");
            while(rs.next()){
                this.uniqueID = uniqueID;
                this.firstName = rs.getString("firstName");
                this.lastName = rs.getString("lastName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    Getters
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public int getAge(){
        return this.age;
    }
    
    public int getID(){
        return this.id;
    }
        
    public String getUniqueID(){
        return this.uniqueID;
    }
    
//    Setters
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
//    Public Functions
    
    public void insert(){
        try {
            try (PreparedStatement stmt = timetableapp.TimeTableApp.con.prepareStatement("INSERT INTO student (uniqueID, firstName, lastName) VALUES(?, ?, ?)")) {
                stmt.setString(1, this.uniqueID);
                stmt.setString(2, this.firstName);
                stmt.setString(3, this.lastName);
                stmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(){
        try {
            try (PreparedStatement stmt = timetableapp.TimeTableApp.con.prepareStatement("UPDATE student SET firstName = ?, lastName = ? WHERE uniqueID = ?")) {
                stmt.setString(1, this.firstName);
                stmt.setString(2, this.lastName);
                stmt.setString(3, this.uniqueID); 
                stmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(){
        try {
            try (PreparedStatement stmt = timetableapp.TimeTableApp.con.prepareStatement("DELETE FROM student WHERE uniqueID = ?")) {
                stmt.setString(1, this.uniqueID);
                stmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    Private Functions
    
    
}
