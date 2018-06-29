/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetableapp;

import helpers.RandomString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author filip
 */
public class TimeTableApp extends Application {
    
    public static Connection con;
    static TimeTableApp app;
    public static Stage window;
    public static Scene mainScene;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLMain.fxml"));
        String css = TimeTableApp.class.getResource("style.css").toExternalForm();  
        
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(css);      
        
        window.setScene(mainScene);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        app = new TimeTableApp();
        app.createConnection();
       
        
        launch(args);
    }
    
    private void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetableapp?useSSL=false&serverTimezone=UTC","root","admin");
            System.out.println("Database Connection Success");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TimeTableApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
