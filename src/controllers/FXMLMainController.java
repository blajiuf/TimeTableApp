/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import helpers.ComboItem;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.animation.Interpolator.EASE_BOTH;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.annotation.PostConstruct;
import javax.swing.table.DefaultTableModel;
import timetableapp.Professor;
import timetableapp.Professors;
import timetableapp.Student;
import timetableapp.Students;
import timetableapp.TimeTable;

/**
 *
 * @author filip
 */
public class FXMLMainController implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private JFXTabPane mainTabPane;
    @FXML
    private Tab studentsTab;
    @FXML
    private Tab timetableTab;
    @FXML
    private JFXMasonryPane studentsMasonry;
    @FXML
    private ScrollPane studentsScrollPane;
    @FXML
    private GridPane anchorPane;
    @FXML
    private JFXButton plusButton;
    @FXML
    private Tab teachersTab;
    @FXML
    private ScrollPane professorsScrollPane;
    @FXML
    private JFXMasonryPane professorsMasonry;
    @FXML
    private JFXTreeTableView<TimeTable> timeTableView;
    private ObservableList<TimeTable> timetable;
    @PostConstruct
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Students students = new Students();
        ArrayList<Student> r = students.getStudents();
        ArrayList<Node> children = new ArrayList<>();
        for (int i = 0; i < r.size(); i++ ) {
            Student student = r.get(i);

            String fn = student.getFirstName();
            String ln = student.getLastName();

            StackPane child = new StackPane();
            child.setId(student.getUniqueID());
            double width = 225;
            child.setPrefWidth(width);
            double height = 200;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);
            children.add(child);

            // create content
            StackPane header = new StackPane();
            String headerColor = getDefaultColor(i % 12);
            header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);

            JFXDialogLayout dialogContent = new JFXDialogLayout();
            JFXTextField firstName = new JFXTextField();
            firstName.setLabelFloat(true);
            firstName.setPromptText("First Name");
            firstName.setText(fn);

            JFXTextField lastName = new JFXTextField();
            lastName.setLabelFloat(true);
            lastName.setPromptText("Last Name");
            lastName.setText(ln);

            GridPane studentForm = new GridPane();
            studentForm.setHgap(10);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(50);
            column1.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column1);

            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(50);
            column2.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column2);

            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.SOMETIMES);
            studentForm.getRowConstraints().add(row1);

            studentForm.getChildren().add(firstName);
            GridPane.setColumnIndex(lastName,1);
            studentForm.getChildren().add(lastName);

            dialogContent.setHeading(new Text("Edit Student"));
            dialogContent.setBody(studentForm);
            JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
            


            JFXButton buttonS = new JFXButton(fn + " " + ln);
            buttonS.setTextFill(Color.web("#fff"));
            buttonS.setFont(new Font(20));
            buttonS.setMinWidth(220);
            buttonS.setMinHeight(120);
            buttonS.setOnAction(e -> {
                dialog.show();
            });


            header.getChildren().add(buttonS);
            
            JFXButton b = new JFXButton("Close");
            b.setOnAction((e) -> {
                dialog.close();
            });
            JFXButton c = new JFXButton("Update");
            c.setOnAction((e) -> {
                System.out.println("c: "+student.getUniqueID());
                if (firstName.getText().isEmpty()) {
                    firstName.validate();
                }
                if (lastName.getText().isEmpty()) {
                    lastName.validate();
                }

                if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
                    student.setFirstName(firstName.getText());
                    student.setLastName(lastName.getText());
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You updated " + student.getFirstName() + " " + student.getLastName() + ".", "OKAY", 5000, handler);
                    buttonS.setText(student.getFirstName() + " " + student.getLastName());
                    student.update();
                    dialog.close();
                }
                dialog.close();
            });
            dialogContent.setActions(b,c);


            VBox.setVgrow(header, Priority.ALWAYS);
            StackPane body = new StackPane();
            body.setMinHeight(100);
            VBox content = new VBox();
            content.getChildren().addAll(header, body);
            body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


            // create button
            JFXButton button = new JFXButton("");
            button.setButtonType(ButtonType.RAISED);
            button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button.setPrefSize(40, 40);
            button.setRipplerFill(Color.valueOf(headerColor));
            button.setScaleX(0);
            button.setScaleY(0);
            SVGGlyph glyph = new SVGGlyph(-1,
                    "test",
                    "M5.198,4.256 L7.388,6.769 C7.703,7.083 8.214,7.083 8.531,6.769 L10.72,4.256 C11.034,3.941 11.083,3.38 10.769,3.066 L8.965,3.066 L8.965,1.144 C8.965,0.585 8.532,0.134 7.997,0.134 C7.462,0.134 7.028,0.585 7.028,1.144 L7.028,3.066 L5.247,3.066 C4.932,3.381 4.883,3.94 5.198,4.256 L5.198,4.256 Z"
                            + "M13.993,1.006 L10.031,1.006 L10.031,1.981 L13.177,1.981 L14.54,9.01 L1.505,9.01 L2.912,1.981 L5.969,1.981 L5.969,1.006 L2.073,1.006 L0.013,9.761 L0.013,13.931 C0.013,15.265 0.485,15.959 1.817,15.959 L14.097,15.959 C15.343,15.959 15.982,15.432 15.982,13.848 L15.982,9.761 L13.993,1.006 L13.993,1.006 Z M10.016,11 L5.985,11 L5.985,9.969 L10.016,9.969 L10.016,11 L10.016,11 Z",
                    Color.WHITE);
            glyph.setSize(20, 20);
            button.setGraphic(glyph);
            button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
            }, header.boundsInParentProperty(), button.heightProperty()));
            StackPane.setMargin(button, new Insets(0, 12, 0, 0));
            StackPane.setAlignment(button, Pos.TOP_RIGHT);
            
            // create button
            JFXButton button2 = new JFXButton("");
            button2.setButtonType(ButtonType.RAISED);
            button2.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button2.setPrefSize(40, 40);
            button2.setRipplerFill(Color.valueOf(headerColor));
            button2.setScaleX(0);
            button2.setScaleY(0);
            SVGGlyph glyph2 = new SVGGlyph(-1,
                    "test",
                    "M0.982,5.073 L2.007,15.339 C2.007,15.705 2.314,16 2.691,16 L10.271,16 C10.648,16 10.955,15.705 10.955,15.339 L11.98,5.073 L0.982,5.073 L0.982,5.073 Z M7.033,14.068 L5.961,14.068 L5.961,6.989 L7.033,6.989 L7.033,14.068 L7.033,14.068 Z M9.033,14.068 L7.961,14.068 L8.961,6.989 L10.033,6.989 L9.033,14.068 L9.033,14.068 Z M5.033,14.068 L3.961,14.068 L2.961,6.989 L4.033,6.989 L5.033,14.068 L5.033,14.068 Z"
                            + "M12.075,2.105 L8.937,2.105 L8.937,0.709 C8.937,0.317 8.481,0 8.081,0 L4.986,0 C4.586,0 4.031,0.225 4.031,0.615 L4.031,2.011 L0.886,2.105 C0.485,2.105 0.159,2.421 0.159,2.813 L0.159,3.968 L12.8,3.968 L12.8,2.813 C12.801,2.422 12.477,2.105 12.075,2.105 L12.075,2.105 Z M4.947,1.44 C4.947,1.128 5.298,0.875 5.73,0.875 L7.294,0.875 C7.726,0.875 8.076,1.129 8.076,1.44 L8.076,2.105 L4.946,2.105 L4.946,1.44 L4.947,1.44 Z",
                    Color.WHITE);
            glyph2.setSize(20, 20);
            button2.setGraphic(glyph2);
            button2.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button2.getHeight() / 2;
            }, header.boundsInParentProperty(), button2.heightProperty()));
            
            StackPane.setMargin(button2, new Insets(0, 0, 0, 12));
            StackPane.setAlignment(button2, Pos.TOP_LEFT);
            
            button2.setOnAction((e) -> {
                JFXDialogLayout deleteAlertL = new JFXDialogLayout();
                

                deleteAlertL.setHeading(new Text("Are you sure you want to delete student " + fn + " " + ln + "?"));
                JFXDialog deleteAlert = new JFXDialog(mainPane, deleteAlertL, JFXDialog.DialogTransition.CENTER);
                deleteAlert.setId("DeleteAlert");
                JFXButton no = new JFXButton("No");
                no.setOnAction((ev) -> {
                    deleteAlert.close();
                });
                JFXButton yes = new JFXButton("Yes");
                yes.setOnAction((ev) -> {
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You deleted " + fn + " " + ln + ".", "OKAY", 5000, handler);
                    student.delete();
                    studentsMasonry.getChildren().remove(child);
                    deleteAlert.close();
                });
                deleteAlertL.setActions(no,yes);
                                
                deleteAlert.show();
            });
            

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation.setDelay(Duration.millis(100 * i + 1000));
            animation.play();
            Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button2.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button2.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation2.setDelay(Duration.millis(100 * i + 1000));
            animation2.play();
            child.getChildren().addAll(content, button, button2);
        }


        studentsMasonry.getChildren().addAll(children);

        Platform.runLater(() -> studentsScrollPane.requestLayout());

        JFXScrollPane.smoothScrolling(studentsScrollPane);  
        
//        Professor

        Professors professors = new Professors();
        ArrayList<Professor> p = professors.getProfessors();
        ArrayList<Node> childrenP = new ArrayList<>();
        for (int j = 0; j < p.size(); j++ ) {
            Professor professor = p.get(j);

            String fn = professor.getFirstName();
            String ln = professor.getLastName();

            StackPane child = new StackPane();
            child.setId(professor.getUniqueID());
            double width = 225;
            child.setPrefWidth(width);
            double height = 200;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);
            childrenP.add(child);

            // create content
            StackPane header = new StackPane();
            String headerColor = getDefaultColor(j % 12);
            header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);

            JFXDialogLayout dialogContent = new JFXDialogLayout();
            JFXTextField firstName = new JFXTextField();
            firstName.setLabelFloat(true);
            firstName.setPromptText("First Name");
            firstName.setText(fn);

            JFXTextField lastName = new JFXTextField();
            lastName.setLabelFloat(true);
            lastName.setPromptText("Last Name");
            lastName.setText(ln);

            GridPane studentForm = new GridPane();
            studentForm.setHgap(10);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(50);
            column1.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column1);

            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(50);
            column2.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column2);

            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.SOMETIMES);
            studentForm.getRowConstraints().add(row1);

            studentForm.getChildren().add(firstName);
            GridPane.setColumnIndex(lastName,1);
            studentForm.getChildren().add(lastName);

            dialogContent.setHeading(new Text("Edit Professor"));
            dialogContent.setBody(studentForm);
            JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
            


            JFXButton buttonS = new JFXButton(fn + " " + ln);
            buttonS.setTextFill(Color.web("#fff"));
            buttonS.setFont(new Font(20));
            buttonS.setMinWidth(220);
            buttonS.setMinHeight(120);
            buttonS.setOnAction(e -> {
                dialog.show();
            });


            header.getChildren().add(buttonS);
            
            JFXButton b = new JFXButton("Close");
            b.setOnAction((e) -> {
                dialog.close();
            });
            JFXButton c = new JFXButton("Update");
            c.setOnAction((e) -> {
                if (firstName.getText().isEmpty()) {
                    firstName.validate();
                }
                if (lastName.getText().isEmpty()) {
                    lastName.validate();
                }

                if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
                    professor.setFirstName(firstName.getText());
                    professor.setLastName(lastName.getText());
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You updated " + professor.getFirstName() + " " + professor.getLastName() + ".", "OKAY", 5000, handler);
                    buttonS.setText(professor.getFirstName() + " " + professor.getLastName());
                    professor.update();
                    dialog.close();
                }
                dialog.close();
            });
            dialogContent.setActions(b,c);


            VBox.setVgrow(header, Priority.ALWAYS);
            StackPane body = new StackPane();
            body.setMinHeight(100);
            VBox content = new VBox();
            content.getChildren().addAll(header, body);
            body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


            // create button
            JFXButton button = new JFXButton("");
            button.setButtonType(ButtonType.RAISED);
            button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button.setPrefSize(40, 40);
            button.setRipplerFill(Color.valueOf(headerColor));
            button.setScaleX(0);
            button.setScaleY(0);
            SVGGlyph glyph = new SVGGlyph(-1,
                    "test",
                    "M5.198,4.256 L7.388,6.769 C7.703,7.083 8.214,7.083 8.531,6.769 L10.72,4.256 C11.034,3.941 11.083,3.38 10.769,3.066 L8.965,3.066 L8.965,1.144 C8.965,0.585 8.532,0.134 7.997,0.134 C7.462,0.134 7.028,0.585 7.028,1.144 L7.028,3.066 L5.247,3.066 C4.932,3.381 4.883,3.94 5.198,4.256 L5.198,4.256 Z"
                            + "M13.993,1.006 L10.031,1.006 L10.031,1.981 L13.177,1.981 L14.54,9.01 L1.505,9.01 L2.912,1.981 L5.969,1.981 L5.969,1.006 L2.073,1.006 L0.013,9.761 L0.013,13.931 C0.013,15.265 0.485,15.959 1.817,15.959 L14.097,15.959 C15.343,15.959 15.982,15.432 15.982,13.848 L15.982,9.761 L13.993,1.006 L13.993,1.006 Z M10.016,11 L5.985,11 L5.985,9.969 L10.016,9.969 L10.016,11 L10.016,11 Z",
                    Color.WHITE);
            glyph.setSize(20, 20);
            button.setGraphic(glyph);
            button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
            }, header.boundsInParentProperty(), button.heightProperty()));
            StackPane.setMargin(button, new Insets(0, 12, 0, 0));
            StackPane.setAlignment(button, Pos.TOP_RIGHT);
            
            // create button
            JFXButton button2 = new JFXButton("");
            button2.setButtonType(ButtonType.RAISED);
            button2.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button2.setPrefSize(40, 40);
            button2.setRipplerFill(Color.valueOf(headerColor));
            button2.setScaleX(0);
            button2.setScaleY(0);
            SVGGlyph glyph2 = new SVGGlyph(-1,
                    "test",
                    "M0.982,5.073 L2.007,15.339 C2.007,15.705 2.314,16 2.691,16 L10.271,16 C10.648,16 10.955,15.705 10.955,15.339 L11.98,5.073 L0.982,5.073 L0.982,5.073 Z M7.033,14.068 L5.961,14.068 L5.961,6.989 L7.033,6.989 L7.033,14.068 L7.033,14.068 Z M9.033,14.068 L7.961,14.068 L8.961,6.989 L10.033,6.989 L9.033,14.068 L9.033,14.068 Z M5.033,14.068 L3.961,14.068 L2.961,6.989 L4.033,6.989 L5.033,14.068 L5.033,14.068 Z"
                            + "M12.075,2.105 L8.937,2.105 L8.937,0.709 C8.937,0.317 8.481,0 8.081,0 L4.986,0 C4.586,0 4.031,0.225 4.031,0.615 L4.031,2.011 L0.886,2.105 C0.485,2.105 0.159,2.421 0.159,2.813 L0.159,3.968 L12.8,3.968 L12.8,2.813 C12.801,2.422 12.477,2.105 12.075,2.105 L12.075,2.105 Z M4.947,1.44 C4.947,1.128 5.298,0.875 5.73,0.875 L7.294,0.875 C7.726,0.875 8.076,1.129 8.076,1.44 L8.076,2.105 L4.946,2.105 L4.946,1.44 L4.947,1.44 Z",
                    Color.WHITE);
            glyph2.setSize(20, 20);
            button2.setGraphic(glyph2);
            button2.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button2.getHeight() / 2;
            }, header.boundsInParentProperty(), button2.heightProperty()));
            
            StackPane.setMargin(button2, new Insets(0, 0, 0, 12));
            StackPane.setAlignment(button2, Pos.TOP_LEFT);
            
            button2.setOnAction((e) -> {
                JFXDialogLayout deleteAlertL = new JFXDialogLayout();
                

                deleteAlertL.setHeading(new Text("Are you sure you want to delete professor " + fn + " " + ln + "?"));
                JFXDialog deleteAlert = new JFXDialog(mainPane, deleteAlertL, JFXDialog.DialogTransition.CENTER);
                deleteAlert.setId("DeleteAlert");
                JFXButton no = new JFXButton("No");
                no.setOnAction((ev) -> {
                    deleteAlert.close();
                });
                JFXButton yes = new JFXButton("Yes");
                yes.setOnAction((ev) -> {
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You deleted " + fn + " " + ln + ".", "OKAY", 5000, handler);
                    professor.delete();
                    professorsMasonry.getChildren().remove(child);
                    deleteAlert.close();
                });
                deleteAlertL.setActions(no,yes);
                                
                deleteAlert.show();
            });
            

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation.setDelay(Duration.millis(100 * j + 1000));
            animation.play();
            Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button2.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button2.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation2.setDelay(Duration.millis(100 * j + 1000));
            animation2.play();
            child.getChildren().addAll(content, button, button2);
        }


        professorsMasonry.getChildren().addAll(childrenP);

        Platform.runLater(() -> professorsScrollPane.requestLayout());

        JFXScrollPane.smoothScrolling(professorsScrollPane);  
        
//        Time Table
        
        JFXTreeTableColumn<TimeTable, String> dayColumn = new JFXTreeTableColumn<>("Day");
        dayColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getDay();
            }
        });
        JFXTreeTableColumn<TimeTable, String> hourColumn = new JFXTreeTableColumn<>("Hour");
        hourColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getHour();
            }
        });
        JFXTreeTableColumn<TimeTable, String> weekColumn = new JFXTreeTableColumn<>("Week");
        weekColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getWeek();
            }
        });
        JFXTreeTableColumn<TimeTable, String> semesterColumn = new JFXTreeTableColumn<>("Semester");
        semesterColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getSemester();
            }
        });
        JFXTreeTableColumn<TimeTable, String> courseColumn = new JFXTreeTableColumn<>("Course");
        courseColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getCourse();
            }
        });
        JFXTreeTableColumn<TimeTable, String> cslpColumn = new JFXTreeTableColumn<>("CSLP");
        cslpColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getCSLP();
            }
        });
        JFXTreeTableColumn<TimeTable, String> roomColumn = new JFXTreeTableColumn<>("Room");
        roomColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getRoom();
            }
        });
        JFXTreeTableColumn<TimeTable, String> professorColumn = new JFXTreeTableColumn<>("Professor");
        professorColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTable, String> param) {
                return param.getValue().getValue().getProfessor();
            }
        });
        
        timetable = new TimeTable().getTimeTable();
        final TreeItem<TimeTable> treeItem = new RecursiveTreeItem<TimeTable>(timetable, RecursiveTreeObject::getChildren);
        
        
        timeTableView.getColumns().setAll(dayColumn, hourColumn, weekColumn, semesterColumn, courseColumn, cslpColumn, roomColumn, professorColumn);
        timeTableView.setRoot(treeItem);
        timeTableView.setShowRoot(false);
        
//        searchTable.textProperty().addListener(new ChangeListener<String>() W{
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                treeView.setPredicate(new Predicate<TreeItem<User>>() {
//                    @Override
//                    public boolean test(TreeItem<User> user) {
//                        Boolean flag = user.getValue().department.getValue().toLowerCase().contains((newValue.toLowerCase())) || user.getValue().userName.getValue().toLowerCase().contains((newValue.toLowerCase()));
//                        return flag;
//                    }
//                });
//            }
//            
//        });
        
    }    
    
    private void addStudentToMasonry(Student student){

            StackPane child = new StackPane();
            child.setId(student.getUniqueID());
            double width = 225;
            child.setPrefWidth(width);
            double height = 200;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);
            
            // create content
            StackPane header = new StackPane();
            String headerColor = getDefaultColor((int) ((Math.random() * 12) % 12));
            header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);

            JFXDialogLayout dialogContent = new JFXDialogLayout();
            JFXTextField firstName = new JFXTextField();
            firstName.setLabelFloat(true);
            firstName.setPromptText("First Name");
            firstName.setText(student.getFirstName());

            JFXTextField lastName = new JFXTextField();
            lastName.setLabelFloat(true);
            lastName.setPromptText("Last Name");
            lastName.setText(student.getLastName());

            GridPane studentForm = new GridPane();
            studentForm.setHgap(10);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(50);
            column1.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column1);

            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(50);
            column2.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column2);

            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.SOMETIMES);
            studentForm.getRowConstraints().add(row1);

            studentForm.getChildren().add(firstName);
            GridPane.setColumnIndex(lastName,1);
            studentForm.getChildren().add(lastName);

            dialogContent.setHeading(new Text("Edit Student"));
            dialogContent.setBody(studentForm);
            JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
            JFXButton b = new JFXButton("Okay");
            b.setOnAction((e) -> {
                dialog.close();
            });
            dialogContent.setActions(b);


            JFXButton buttonS = new JFXButton(student.getFirstName() + " " + student.getLastName());
            buttonS.setTextFill(Color.web("#fff"));
            buttonS.setFont(new Font(20));
            buttonS.setMinWidth(220);
            buttonS.setMinHeight(120);


            header.getChildren().add(buttonS);

            JFXButton closeDialogStudent = new JFXButton("Close");
            closeDialogStudent.setOnAction((e) -> {
                dialog.close();
            });
            JFXButton updateDialogStudent = new JFXButton("Update");
            updateDialogStudent.setOnAction((e) -> {
                if (firstName.getText().isEmpty()) {
                    firstName.validate();
                }
                if (lastName.getText().isEmpty()) {
                    lastName.validate();
                }

                if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
                    student.setFirstName(firstName.getText());
                    student.setLastName(lastName.getText());
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You updated " + student.getFirstName() + " " + student.getLastName() + ".", "OKAY", 5000, handler);
                    buttonS.setText(student.getFirstName() + " " + student.getLastName());
                    student.update();
                    dialog.close();
                }
                dialog.close();
            });
            dialogContent.setActions(closeDialogStudent,updateDialogStudent);

            VBox.setVgrow(header, Priority.ALWAYS);
            StackPane body = new StackPane();
            body.setMinHeight(100);
            VBox content = new VBox();
            content.getChildren().addAll(header, body);
            body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


            // create button
            JFXButton button = new JFXButton("");
            button.setButtonType(ButtonType.RAISED);
            button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button.setPrefSize(40, 40);
            button.setRipplerFill(Color.valueOf(headerColor));
            button.setScaleX(0);
            button.setScaleY(0);
            SVGGlyph glyph = new SVGGlyph(-1,
                    "test",
                    "M5.198,4.256 L7.388,6.769 C7.703,7.083 8.214,7.083 8.531,6.769 L10.72,4.256 C11.034,3.941 11.083,3.38 10.769,3.066 L8.965,3.066 L8.965,1.144 C8.965,0.585 8.532,0.134 7.997,0.134 C7.462,0.134 7.028,0.585 7.028,1.144 L7.028,3.066 L5.247,3.066 C4.932,3.381 4.883,3.94 5.198,4.256 L5.198,4.256 Z"
                            + "M13.993,1.006 L10.031,1.006 L10.031,1.981 L13.177,1.981 L14.54,9.01 L1.505,9.01 L2.912,1.981 L5.969,1.981 L5.969,1.006 L2.073,1.006 L0.013,9.761 L0.013,13.931 C0.013,15.265 0.485,15.959 1.817,15.959 L14.097,15.959 C15.343,15.959 15.982,15.432 15.982,13.848 L15.982,9.761 L13.993,1.006 L13.993,1.006 Z M10.016,11 L5.985,11 L5.985,9.969 L10.016,9.969 L10.016,11 L10.016,11 Z",
                    Color.WHITE);
            glyph.setSize(20, 20);
            button.setGraphic(glyph);
            button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
            }, header.boundsInParentProperty(), button.heightProperty()));
            StackPane.setMargin(button, new Insets(0, 12, 0, 0));
            StackPane.setAlignment(button, Pos.TOP_RIGHT);
            
            // create button
            JFXButton button2 = new JFXButton("");
            button2.setButtonType(ButtonType.RAISED);
            button2.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button2.setPrefSize(40, 40);
            button2.setRipplerFill(Color.valueOf(headerColor));
            button2.setScaleX(0);
            button2.setScaleY(0);
            SVGGlyph glyph2 = new SVGGlyph(-1,
                    "test",
                    "M0.982,5.073 L2.007,15.339 C2.007,15.705 2.314,16 2.691,16 L10.271,16 C10.648,16 10.955,15.705 10.955,15.339 L11.98,5.073 L0.982,5.073 L0.982,5.073 Z M7.033,14.068 L5.961,14.068 L5.961,6.989 L7.033,6.989 L7.033,14.068 L7.033,14.068 Z M9.033,14.068 L7.961,14.068 L8.961,6.989 L10.033,6.989 L9.033,14.068 L9.033,14.068 Z M5.033,14.068 L3.961,14.068 L2.961,6.989 L4.033,6.989 L5.033,14.068 L5.033,14.068 Z"
                            + "M12.075,2.105 L8.937,2.105 L8.937,0.709 C8.937,0.317 8.481,0 8.081,0 L4.986,0 C4.586,0 4.031,0.225 4.031,0.615 L4.031,2.011 L0.886,2.105 C0.485,2.105 0.159,2.421 0.159,2.813 L0.159,3.968 L12.8,3.968 L12.8,2.813 C12.801,2.422 12.477,2.105 12.075,2.105 L12.075,2.105 Z M4.947,1.44 C4.947,1.128 5.298,0.875 5.73,0.875 L7.294,0.875 C7.726,0.875 8.076,1.129 8.076,1.44 L8.076,2.105 L4.946,2.105 L4.946,1.44 L4.947,1.44 Z",
                    Color.WHITE);
            glyph2.setSize(20, 20);
            button2.setGraphic(glyph2);
            button2.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button2.getHeight() / 2;
            }, header.boundsInParentProperty(), button2.heightProperty()));
            
            StackPane.setMargin(button2, new Insets(0, 0, 0, 12));
            StackPane.setAlignment(button2, Pos.TOP_LEFT);
            
            button2.setOnAction((e) -> {
                JFXDialogLayout deleteAlertL = new JFXDialogLayout();
                

                deleteAlertL.setHeading(new Text("Are you sure you want to delete student " + student.getFirstName() + " " + student.getLastName() + "?"));
                JFXDialog deleteAlert = new JFXDialog(mainPane, deleteAlertL, JFXDialog.DialogTransition.CENTER);
                JFXButton no = new JFXButton("No");
                no.setOnAction((ev) -> {
                    deleteAlert.close();
                });
                JFXButton yes = new JFXButton("Yes");
                yes.setOnAction((ev) -> {
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You deleted " + student.getFirstName() + " " + student.getLastName()+ ".", "OKAY", 5000, handler);
                    student.delete();
                    studentsMasonry.getChildren().remove(child);
                    deleteAlert.close();
                });
                deleteAlertL.setActions(no,yes);
                deleteAlert.show();
            });

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation.setDelay(Duration.millis(1000));
            animation.play();
            Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button2.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button2.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation2.setDelay(Duration.millis(1000));
            animation2.play();
            child.getChildren().addAll(content, button, button2);
        


        studentsMasonry.getChildren().add(child);

        Platform.runLater(() -> studentsScrollPane.requestLayout());

        JFXScrollPane.smoothScrolling(studentsScrollPane); 
    }   
    
    private void addProfessorToMasonry(Professor student){

            StackPane child = new StackPane();
            child.setId(student.getUniqueID());
            double width = 225;
            child.setPrefWidth(width);
            double height = 200;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);
            
            // create content
            StackPane header = new StackPane();
            String headerColor = getDefaultColor((int) ((Math.random() * 12) % 12));
            header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);

            JFXDialogLayout dialogContent = new JFXDialogLayout();
            JFXTextField firstName = new JFXTextField();
            firstName.setLabelFloat(true);
            firstName.setPromptText("First Name");
            firstName.setText(student.getFirstName());

            JFXTextField lastName = new JFXTextField();
            lastName.setLabelFloat(true);
            lastName.setPromptText("Last Name");
            lastName.setText(student.getLastName());

            GridPane studentForm = new GridPane();
            studentForm.setHgap(10);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(50);
            column1.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column1);

            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(50);
            column2.setHgrow(Priority.SOMETIMES);
            studentForm.getColumnConstraints().add(column2);

            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.SOMETIMES);
            studentForm.getRowConstraints().add(row1);

            studentForm.getChildren().add(firstName);
            GridPane.setColumnIndex(lastName,1);
            studentForm.getChildren().add(lastName);

            dialogContent.setHeading(new Text("Edit Professor"));
            dialogContent.setBody(studentForm);
            JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
            
            


            JFXButton buttonS = new JFXButton(student.getFirstName() + " " + student.getLastName());
            buttonS.setTextFill(Color.web("#fff"));
            buttonS.setFont(new Font(20));
            buttonS.setMinWidth(220);
            buttonS.setMinHeight(120);
            buttonS.setOnAction(e -> {
                dialog.show();
            });


            header.getChildren().add(buttonS);
            
            

            JFXButton closeDialogProfessor = new JFXButton("Close");
            closeDialogProfessor.setOnAction((e) -> {
                dialog.close();
            });
            JFXButton updateDialogProfessor = new JFXButton("Update");
            updateDialogProfessor.setOnAction((e) -> {
                if (firstName.getText().isEmpty()) {
                    firstName.validate();
                }
                if (lastName.getText().isEmpty()) {
                    lastName.validate();
                }

                if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
                    student.setFirstName(firstName.getText());
                    student.setLastName(lastName.getText());
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You updated " + student.getFirstName() + " " + student.getLastName() + ".", "OKAY", 5000, handler);
                    buttonS.setText(student.getFirstName() + " " + student.getLastName());
                    student.update();
                    dialog.close();
                }
                dialog.close();
            });
            dialogContent.setActions(closeDialogProfessor,updateDialogProfessor);

            VBox.setVgrow(header, Priority.ALWAYS);
            StackPane body = new StackPane();
            body.setMinHeight(100);
            VBox content = new VBox();
            content.getChildren().addAll(header, body);
            body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


            // create button
            JFXButton button = new JFXButton("");
            button.setButtonType(ButtonType.RAISED);
            button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button.setPrefSize(40, 40);
            button.setRipplerFill(Color.valueOf(headerColor));
            button.setScaleX(0);
            button.setScaleY(0);
            SVGGlyph glyph = new SVGGlyph(-1,
                    "test",
                    "M5.198,4.256 L7.388,6.769 C7.703,7.083 8.214,7.083 8.531,6.769 L10.72,4.256 C11.034,3.941 11.083,3.38 10.769,3.066 L8.965,3.066 L8.965,1.144 C8.965,0.585 8.532,0.134 7.997,0.134 C7.462,0.134 7.028,0.585 7.028,1.144 L7.028,3.066 L5.247,3.066 C4.932,3.381 4.883,3.94 5.198,4.256 L5.198,4.256 Z"
                            + "M13.993,1.006 L10.031,1.006 L10.031,1.981 L13.177,1.981 L14.54,9.01 L1.505,9.01 L2.912,1.981 L5.969,1.981 L5.969,1.006 L2.073,1.006 L0.013,9.761 L0.013,13.931 C0.013,15.265 0.485,15.959 1.817,15.959 L14.097,15.959 C15.343,15.959 15.982,15.432 15.982,13.848 L15.982,9.761 L13.993,1.006 L13.993,1.006 Z M10.016,11 L5.985,11 L5.985,9.969 L10.016,9.969 L10.016,11 L10.016,11 Z",
                    Color.WHITE);
            glyph.setSize(20, 20);
            button.setGraphic(glyph);
            button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
            }, header.boundsInParentProperty(), button.heightProperty()));
            StackPane.setMargin(button, new Insets(0, 12, 0, 0));
            StackPane.setAlignment(button, Pos.TOP_RIGHT);
            
            // create button
            JFXButton button2 = new JFXButton("");
            button2.setButtonType(ButtonType.RAISED);
            button2.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button2.setPrefSize(40, 40);
            button2.setRipplerFill(Color.valueOf(headerColor));
            button2.setScaleX(0);
            button2.setScaleY(0);
            SVGGlyph glyph2 = new SVGGlyph(-1,
                    "test",
                    "M0.982,5.073 L2.007,15.339 C2.007,15.705 2.314,16 2.691,16 L10.271,16 C10.648,16 10.955,15.705 10.955,15.339 L11.98,5.073 L0.982,5.073 L0.982,5.073 Z M7.033,14.068 L5.961,14.068 L5.961,6.989 L7.033,6.989 L7.033,14.068 L7.033,14.068 Z M9.033,14.068 L7.961,14.068 L8.961,6.989 L10.033,6.989 L9.033,14.068 L9.033,14.068 Z M5.033,14.068 L3.961,14.068 L2.961,6.989 L4.033,6.989 L5.033,14.068 L5.033,14.068 Z"
                            + "M12.075,2.105 L8.937,2.105 L8.937,0.709 C8.937,0.317 8.481,0 8.081,0 L4.986,0 C4.586,0 4.031,0.225 4.031,0.615 L4.031,2.011 L0.886,2.105 C0.485,2.105 0.159,2.421 0.159,2.813 L0.159,3.968 L12.8,3.968 L12.8,2.813 C12.801,2.422 12.477,2.105 12.075,2.105 L12.075,2.105 Z M4.947,1.44 C4.947,1.128 5.298,0.875 5.73,0.875 L7.294,0.875 C7.726,0.875 8.076,1.129 8.076,1.44 L8.076,2.105 L4.946,2.105 L4.946,1.44 L4.947,1.44 Z",
                    Color.WHITE);
            glyph2.setSize(20, 20);
            button2.setGraphic(glyph2);
            button2.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button2.getHeight() / 2;
            }, header.boundsInParentProperty(), button2.heightProperty()));
            
            StackPane.setMargin(button2, new Insets(0, 0, 0, 12));
            StackPane.setAlignment(button2, Pos.TOP_LEFT);
            
            button2.setOnAction((e) -> {
                JFXDialogLayout deleteAlertL = new JFXDialogLayout();
                

                deleteAlertL.setHeading(new Text("Are you sure you want to delete professor " + student.getFirstName() + " " + student.getLastName() + "?"));
                JFXDialog deleteAlert = new JFXDialog(mainPane, deleteAlertL, JFXDialog.DialogTransition.CENTER);
                JFXButton no = new JFXButton("No");
                no.setOnAction((ev) -> {
                    deleteAlert.close();
                });
                JFXButton yes = new JFXButton("Yes");
                yes.setOnAction((ev) -> {
                    JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                    EventHandler handler = new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            System.out.println("You clicked");
                            snackbar.unregisterSnackbarContainer(anchorPane);
                        }
                    };
                    snackbar.show("You deleted " + student.getFirstName() + " " + student.getLastName()+ ".", "OKAY", 5000, handler);
                    student.delete();
                    professorsMasonry.getChildren().remove(child);
                    deleteAlert.close();
                });
                deleteAlertL.setActions(no,yes);
                deleteAlert.show();
            });

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation.setDelay(Duration.millis(1000));
            animation.play();
            Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button2.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button2.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation2.setDelay(Duration.millis(1000));
            animation2.play();
            child.getChildren().addAll(content, button, button2);
        


        professorsMasonry.getChildren().add(child);

        Platform.runLater(() -> professorsScrollPane.requestLayout());

        JFXScrollPane.smoothScrolling(professorsScrollPane); 
    }
    
    private String getDefaultColor(int i) {
        String color = "#FFFFFF";
        switch (i) {
        case 0:
            color = "#8F3F7E";
            break;
        case 1:
            color = "#B5305F";
            break;
        case 2:
            color = "#CE584A";
            break;
        case 3:
            color = "#DB8D5C";
            break;
        case 4:
            color = "#DA854E";
            break;
        case 5:
            color = "#E9AB44";
            break;
        case 6:
            color = "#FEE435";
            break;
        case 7:
            color = "#99C286";
            break;
        case 8:
            color = "#01A05E";
            break;
        case 9:
            color = "#4A8895";
            break;
        case 10:
            color = "#16669B";
            break;
        case 11:
            color = "#2F65A5";
            break;
        case 12:
            color = "#4E6A9C";
            break;
        default:
            break;
        }
        return color;
    }

    @FXML
    private void plusAction(ActionEvent event) {
        if(mainTabPane.getSelectionModel().getSelectedIndex() == 0){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text("Add Student"));
        
        JFXTextField firstName = new JFXTextField();            
        firstName.setLabelFloat(true);
        firstName.setPromptText("First Name");
        
        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        firstName.getValidators().add(validator1);
        validator1.setMessage("No Input Given");
        
        firstName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    firstName.validate();
                }
            }
        });

        JFXTextField lastName = new JFXTextField();
        lastName.setLabelFloat(true);
        lastName.setPromptText("Last Name");
        
        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        lastName.getValidators().add(validator2);
        validator2.setMessage("No Input Given");
        
        lastName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    lastName.validate();
                }
            }
        });
        
        GridPane studentForm = new GridPane();
        studentForm.setHgap(10);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        column2.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column2);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        studentForm.getRowConstraints().add(row1);

        studentForm.getChildren().add(firstName);
        GridPane.setColumnIndex(lastName,1);
        studentForm.getChildren().add(lastName);
        dialogContent.setBody(studentForm);
        JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
        JFXButton b = new JFXButton("Close");
        b.setOnAction((e) -> {
            
            dialog.close();
        });
        JFXButton c = new JFXButton("Submit");
                
        c.setOnAction((e) -> {
            if (firstName.getText().isEmpty()) {
                firstName.validate();
            }
            if (lastName.getText().isEmpty()) {
                lastName.validate();
            }
            
            if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
                String fn = firstName.getText();
                String ln = lastName.getText();
                Student s = new Student(fn, ln);
                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                EventHandler handler = new EventHandler(){
                    @Override
                    public void handle(Event event) {
                        System.out.println("You clicked");
                        snackbar.unregisterSnackbarContainer(anchorPane);
                    }
                };
                snackbar.show("You added " + fn + " " + ln + ".", "OKAY", 5000, handler);
                s.insert();
                addStudentToMasonry(s);
                dialog.close();
            }
            
        });
        dialogContent.setActions(b,c);
        dialog.show();
        } else if(mainTabPane.getSelectionModel().getSelectedIndex() == 1){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text("Add Professor"));
        
        JFXTextField firstName = new JFXTextField();            
        firstName.setLabelFloat(true);
        firstName.setPromptText("First Name");
        
        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        firstName.getValidators().add(validator1);
        validator1.setMessage("No Input Given");
        
        firstName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    firstName.validate();
                }
            }
        });

        JFXTextField lastName = new JFXTextField();
        lastName.setLabelFloat(true);
        lastName.setPromptText("Last Name");
        
        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        lastName.getValidators().add(validator2);
        validator2.setMessage("No Input Given");
        
        lastName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    lastName.validate();
                }
            }
        });
        
        GridPane studentForm = new GridPane();
        studentForm.setHgap(10);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        column2.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column2);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        studentForm.getRowConstraints().add(row1);

        studentForm.getChildren().add(firstName);
        GridPane.setColumnIndex(lastName,1);
        studentForm.getChildren().add(lastName);
        dialogContent.setBody(studentForm);
        JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
        JFXButton b = new JFXButton("Close");
        b.setOnAction((e) -> {
            
            dialog.close();
        });
        JFXButton c = new JFXButton("Submit");
                
        c.setOnAction((e) -> {
            if (firstName.getText().isEmpty()) {
                firstName.validate();
            }
            if (lastName.getText().isEmpty()) {
                lastName.validate();
            }
            
            if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
                String fn = firstName.getText();
                String ln = lastName.getText();
                Professor p = new Professor(fn, ln);
                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                EventHandler handler = new EventHandler(){
                    @Override
                    public void handle(Event event) {
                        System.out.println("You clicked");
                        snackbar.unregisterSnackbarContainer(anchorPane);
                    }
                };
                snackbar.show("You added " + fn + " " + ln + ".", "OKAY", 5000, handler);
                p.insert();
                addProfessorToMasonry(p);
                dialog.close();
            }
            
        });
        dialogContent.setActions(b,c);
        dialog.show();
        }else if(mainTabPane.getSelectionModel().getSelectedIndex() == 2){
            TimeTable t = new TimeTable();
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text("Add Time Table Entry"));
        
        JFXComboBox<ComboItem> fieldDay = new JFXComboBox<>(t.optionDay());
        fieldDay.setLabelFloat(true);
        fieldDay.setPromptText("Day");
        JFXComboBox<ComboItem> fieldHour = new JFXComboBox<>(t.optionHour());
        fieldHour.setLabelFloat(true);
        fieldHour.setPromptText("Hour");
        JFXComboBox<ComboItem> fieldWeek = new JFXComboBox<>(t.optionWeek());
        fieldWeek.setLabelFloat(true);
        fieldWeek.setPromptText("Week");
        JFXComboBox<ComboItem> fieldSemester = new JFXComboBox<>(t.optionSemester());
        fieldSemester.setLabelFloat(true);
        fieldSemester.setPromptText("Semester");
        JFXComboBox<ComboItem> fieldCourse = new JFXComboBox<>(t.optionCourse());
        fieldCourse.setLabelFloat(true);
        fieldCourse.setPromptText("Course");
        JFXComboBox<ComboItem> fieldCSLP = new JFXComboBox<>(t.optionCSLP());
        fieldCSLP.setLabelFloat(true);
        fieldCSLP.setPromptText("CSLP");
        JFXComboBox<ComboItem> fieldRoom = new JFXComboBox<>(t.optionRoom());
        fieldRoom.setLabelFloat(true);
        fieldRoom.setPromptText("Room");
        JFXComboBox<ComboItem> fieldProfessor = new JFXComboBox<>(t.optionProfessor());
        fieldProfessor.setLabelFloat(true);
        fieldProfessor.setPromptText("Professor");
          
                    
        
        GridPane studentForm = new GridPane();
        studentForm.setHgap(10);
        studentForm.setVgap(20);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        column1.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(25);
        column2.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column2);
        
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(25);
        column3.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column3);
        
        
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(25);
        column4.setHgrow(Priority.SOMETIMES);
        studentForm.getColumnConstraints().add(column4);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        studentForm.getRowConstraints().add(row1);
        
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.SOMETIMES);
        studentForm.getRowConstraints().add(row2);

        VBox vDay = new VBox(addFieldValidationComboBox(fieldDay));
        VBox vHour = new VBox(addFieldValidationComboBox(fieldHour));
        VBox vWeek = new VBox(addFieldValidationComboBox(fieldWeek));
        VBox vSemester = new VBox(addFieldValidationComboBox(fieldSemester));
        VBox vCourse = new VBox(addFieldValidationComboBox(fieldCourse));
        VBox vCSLP = new VBox(addFieldValidationComboBox(fieldCSLP));
        VBox vRoom = new VBox(addFieldValidationComboBox(fieldRoom));
        VBox vProfessor = new VBox(addFieldValidationComboBox(fieldProfessor));
        
        studentForm.getChildren().add(vDay);
        GridPane.setColumnIndex(vHour,1);
        GridPane.setRowIndex(vHour, 0);
        studentForm.getChildren().add(vHour);
        GridPane.setColumnIndex(vWeek,2);
        GridPane.setRowIndex(vWeek, 0);
        studentForm.getChildren().add(vWeek);
        GridPane.setColumnIndex(vSemester,3);
        GridPane.setRowIndex(vSemester, 0);
        studentForm.getChildren().add(vSemester);
        GridPane.setColumnIndex(vCourse,0);
        GridPane.setRowIndex(vCourse, 1);
        studentForm.getChildren().add(vCourse);
        GridPane.setColumnIndex(vCSLP,1);
        GridPane.setRowIndex(vCSLP, 1);
        studentForm.getChildren().add(vCSLP);
        GridPane.setColumnIndex(vRoom,2);
        GridPane.setRowIndex(vRoom, 1);
        studentForm.getChildren().add(vRoom);
        GridPane.setColumnIndex(vProfessor,3);
        GridPane.setRowIndex(vProfessor, 1);
        studentForm.getChildren().add(vProfessor);
        
        dialogContent.setBody(studentForm);
        JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
        
        
        JFXButton b = new JFXButton("Close");
        b.setOnAction((e) -> {
            dialog.close();
        });
        JFXButton c = new JFXButton("Submit");
                
        c.setOnAction((e) -> {
            

            boolean fieldError = true;

            if(fieldDay.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldDay);
                fieldError = false;
            }
            if(fieldHour.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldHour);
                fieldError = false;
            }
            if(fieldWeek.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldWeek);
                fieldError = false;
            }
            if(fieldSemester.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldSemester);
                fieldError = false;
            }
            if(fieldCourse.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldCourse);
                fieldError = false;
            }
            if(fieldCSLP.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldCSLP);
                fieldError = false;
            }
            if(fieldRoom.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldRoom);
                fieldError = false;
            }
            if(fieldProfessor.getSelectionModel().getSelectedIndex() == -1){
                ValidationFacade.validate(fieldProfessor);
                fieldError = false;
            }
            
            if(fieldError){
                String fDay = fieldDay.getSelectionModel().getSelectedItem().getValue();
                String fHour = fieldHour.getSelectionModel().getSelectedItem().getValue();
                String fWeek = fieldWeek.getSelectionModel().getSelectedItem().getValue();
                String fSemester = fieldSemester.getSelectionModel().getSelectedItem().getValue();
                String fCourse = fieldCourse.getSelectionModel().getSelectedItem().getValue();
                String fCSLP = fieldCSLP.getSelectionModel().getSelectedItem().getValue();
                String fRoom = fieldRoom.getSelectionModel().getSelectedItem().getValue();
                String fProfessor = fieldProfessor.getSelectionModel().getSelectedItem().getValue();
                
                TimeTable timeTable = new TimeTable(fDay, fHour, fWeek, fSemester, fCourse, fCSLP, fRoom, fProfessor);
                timeTable.insert();
                timetable.add(timeTable);
            }
            
//            if (firstName.getText().isEmpty()) {
//                firstName.validate();
//            }
//            if (lastName.getText().isEmpty()) {
//                lastName.validate();
//            }
//            
//            if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
//                String fn = firstName.getText();
//                String ln = lastName.getText();
//                Professor p = new Professor(fn, ln);
//                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
//                EventHandler handler = new EventHandler(){
//                    @Override
//                    public void handle(Event event) {
//                        System.out.println("You clicked");
//                        snackbar.unregisterSnackbarContainer(anchorPane);
//                    }
//                };
//                snackbar.show("You added " + fn + " " + ln + ".", "OKAY", 5000, handler);
//                p.insert();
//                addProfessorToMasonry(p);
//                dialog.close();
//            };
            
        });
        dialogContent.setActions(b,c);
        dialog.show();
        }
    }
    
    private ValidationFacade addFieldValidationComboBox(JFXComboBox field){
        ValidationFacade facade = new ValidationFacade();
        facade.setControl(field);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Select a value");
        facade.getValidators().add(validator);
        
        field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    ValidationFacade.validate(field);
                }
            }
        });
        return facade;
    }
    
}
