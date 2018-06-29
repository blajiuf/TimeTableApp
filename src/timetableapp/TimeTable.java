/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetableapp;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import helpers.ComboItem;
import helpers.RandomString;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author filip
 */
public class TimeTable extends RecursiveTreeObject<TimeTable>{
    private String uniqueID;
    private StringProperty day;
    private StringProperty hour;
    private StringProperty week;
    private StringProperty semester;
    private StringProperty course;
    private StringProperty cslp;
//    private StringProperty faculty;
    private StringProperty room;
    private StringProperty professor;
    
    public TimeTable(){}

//    public TimeTable(String day, String hour, String week, String semester, String course, String cslp, String faculty, String room, String professor){
//        this.uniqueID = new RandomString().nextString();
//        this.day = new SimpleStringProperty(day);
//        this.hour = new SimpleStringProperty(hour);
//        this.week = new SimpleStringProperty(week);
//        this.semester = new SimpleStringProperty(semester);
//        this.course = new SimpleStringProperty(course);
//        this.cslp = new SimpleStringProperty(cslp);
//        this.faculty = new SimpleStringProperty(faculty);
//        this.room = new SimpleStringProperty(room);
//        this.professor = new SimpleStringProperty(professor);
//    }
    public TimeTable(String day, String hour, String week, String semester, String course, String cslp, String room, String professor){
        this.uniqueID = new RandomString().nextString();
        this.day = new SimpleStringProperty(day);
        this.hour = new SimpleStringProperty(hour);
        this.week = new SimpleStringProperty(week);
        this.semester = new SimpleStringProperty(semester);
        this.course = new SimpleStringProperty(course);
        this.cslp = new SimpleStringProperty(cslp);
        this.room = new SimpleStringProperty(room);
        this.professor = new SimpleStringProperty(professor);
    }
    
//    public TimeTable(String uniqueID){
//        try {
//            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM timetable WHERE uniqueID = '" + uniqueID + "' LIMIT 1");
//            while(rs.next()){
//                this.uniqueID = uniqueID;
//                this.day = new SimpleStringProperty(rs.getString("day"));
//                this.hour = new SimpleStringProperty(rs.getString("hour"));
//                this.week = new SimpleStringProperty(rs.getString("week"));
//                this.semester = new SimpleStringProperty(rs.getString("semester"));
//                this.course = new SimpleStringProperty(rs.getString("course"));
//                this.cslp = new SimpleStringProperty(rs.getString("cslp"));
//                this.faculty = new SimpleStringProperty(rs.getString("faculty"));
//                this.room = new SimpleStringProperty(rs.getString("room"));
//                this.professor = new SimpleStringProperty(rs.getString("professor"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public TimeTable(String uniqueID){
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM timetable WHERE uniqueID = '" + uniqueID + "' LIMIT 1");
            while(rs.next()){
                this.uniqueID = uniqueID;
                this.day = new SimpleStringProperty(getDayValue(rs.getInt("day")));
                this.hour = new SimpleStringProperty(getHourValue(rs.getInt("hour")));
                this.week = new SimpleStringProperty(getWeekValue(rs.getInt("week")));
                this.semester = new SimpleStringProperty(getSemesterValue(rs.getInt("semester")));
                this.course = new SimpleStringProperty(getCourseValue(rs.getInt("course")));
                this.cslp = new SimpleStringProperty(getCSLPValue(rs.getInt("cslp")));
//                this.faculty = new SimpleStringProperty(getDayValue(rs.getInt("faculty")));
                this.room = new SimpleStringProperty(getRoomValue(rs.getInt("room")));
                this.professor = new SimpleStringProperty(getProfessorValue(rs.getInt("professor")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    Getters
    
    public String getUniqueID(){
        return this.uniqueID;
    }
    
    public StringProperty getDay(){
        return this.day;
    }
    
    public StringProperty getHour(){
        return this.hour;
    }
    
    public StringProperty getWeek(){
        return this.week;
    }
    
    public StringProperty getSemester(){
        return this.semester;
    }
    
    public StringProperty getCourse(){
        return this.course;
    }
    
    public StringProperty getCSLP(){
        return this.cslp;
    }
    
//    public StringProperty getFaculty(){
//        return this.faculty;
//    }
    
    public StringProperty getRoom(){
        return this.room;
    }
    
    public StringProperty getProfessor(){
        return this.professor;
    }
    
    
    public ObservableList<TimeTable> getTimeTable(){
        ObservableList<TimeTable> timetable = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM timetable");
            while(rs.next()){
                String uniqueID = rs.getString("uniqueID");
                TimeTable entry = new TimeTable(uniqueID);
                timetable.add(entry);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timetable;      
    }
    
//    Setters
    
    public void setDay(StringProperty day){
        this.day = day;
    }
    
    public void setHour(StringProperty hour){
        this.hour = hour;
    }
    
    public void setWeek(StringProperty week){
        this.week = week;
    }
    
    public void setSemester(StringProperty semester){
        this.semester = semester;
    }
    
    public void setCourse(StringProperty course){
        this.course = course;
    }
    
    public void setCSLP(StringProperty cslp){
        this.cslp = cslp;
    }
    
//    public void setFaculty(StringProperty faculty){
//        this.faculty = faculty;
//    }
    
    public void setRoom(StringProperty room){
        this.room = room;
    }
    
    public void setProfessor(StringProperty professor){
        this.professor = professor;
    }
    
//    Public Functions
    
    public ObservableList<ComboItem> optionDay(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM day");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("day"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
             Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionHour(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hour");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("hour"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionWeek(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM week");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("week"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionSemester(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM semester");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("semester"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionCourse(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM course");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("course"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionCSLP(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cslp");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("cslp"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionRoom(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("room"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public ObservableList<ComboItem> optionProfessor(){
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM professor");
            while(rs.next()){
                ComboItem item = new ComboItem(rs.getString("id"), rs.getString("firstName") + " " + rs.getString("lastName"));
                list.add(item);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return list;
    }
    
    public String getDayValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM day WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("day");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getHourValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hour WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("hour");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getWeekValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM week WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("week");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getSemesterValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM semester WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("semester");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getCourseValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM course WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("course");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getCSLPValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cslp WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("cslp");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getRoomValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("room");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    public String getProfessorValue(int id){
        String item = null;
        try {
            Statement stmt = timetableapp.TimeTableApp.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM professor WHERE id = '" + id + "' LIMIT 1");
            while(rs.next()){
                item = rs.getString("firstName") + " " + rs.getString("lastName");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return item;
    }
    
    
    
    public void insert(){
        try {
            try (PreparedStatement stmt = timetableapp.TimeTableApp.con.prepareStatement("INSERT INTO timetable (uniqueID, day, hour, week, semester, course, cslp, room, professor) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, this.uniqueID);
                stmt.setString(2, this.day.get());
                stmt.setString(3, this.hour.get());
                stmt.setString(4, this.week.get());
                stmt.setString(5, this.semester.get());
                stmt.setString(6, this.course.get());
                stmt.setString(7, this.cslp.get());
                stmt.setString(8, this.room.get());
                stmt.setString(9, this.professor.get());
                stmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(){
        try {
            try (PreparedStatement stmt = timetableapp.TimeTableApp.con.prepareStatement("UPDATE timetable SET day = ?, hour = ?, week = ?, semester = ?, course = ?, cslp = ?, room = ?, professor = ? WHERE uniqueID = ?")) {
                stmt.setString(1, this.day.get());
                stmt.setString(2, this.hour.get());
                stmt.setString(3, this.week.get());
                stmt.setString(4, this.semester.get());
                stmt.setString(5, this.course.get());
                stmt.setString(6, this.cslp.get());
                stmt.setString(7, this.room.get());
                stmt.setString(8, this.professor.get());
                stmt.setString(9, this.uniqueID);
                stmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(){
        try {
            try (PreparedStatement stmt = timetableapp.TimeTableApp.con.prepareStatement("DELETE FROM timetable WHERE uniqueID = ?")) {
                stmt.setString(1, this.uniqueID);
                stmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
