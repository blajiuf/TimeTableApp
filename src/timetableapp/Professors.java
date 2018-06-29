/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetableapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class Professors {
    
    public Professors(){
    
    }
    
    public ArrayList<Professor> getProfessors(){
        ArrayList<Professor> professors = new ArrayList<>();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM professor");
            while(rs.next()){
                String uniqueID = rs.getString("uniqueID");
                Professor student = new Professor(uniqueID);
                professors.add(student);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }
        return professors;      
    }
    
}
