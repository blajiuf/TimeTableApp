///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package helpers;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXButton.ButtonType;
//import com.jfoenix.controls.JFXDialog;
//import com.jfoenix.controls.JFXDialogLayout;
//import com.jfoenix.controls.JFXSnackbar;
//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.effects.JFXDepthManager;
//import com.jfoenix.svg.SVGGlyph;
//import javafx.beans.binding.Bindings;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.RowConstraints;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.util.Duration;
//import timetableapp.Student;
//
///**
// *
// * @author filip
// */
//public class StudentCard {
//    
//    StudentCard(StackPane mainPane, Student student){
//        String firstNameS = student.getFirstName();
//        String lastNameS = student.getLastName();
//        
//        
//        StackPane child = new StackPane();
//        child.setId(String.valueOf(student.getID()));
//        double width = 225;
//        child.setPrefWidth(width);
//        double height = 200;
//        child.setPrefHeight(height);
//        JFXDepthManager.setDepth(child, 1);
//        children.add(child);
//
//        // create content
//        StackPane header = new StackPane();
//        String headerColor = getDefaultColor(i % 12);
//        header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);
//
//        JFXDialogLayout dialogContent = new JFXDialogLayout();
//        JFXTextField firstNameField = new JFXTextField();
//        firstNameField.setLabelFloat(true);
//        firstNameField.setPromptText("First Name");
//        firstNameField.setText(student.getFirstName());
//
//        JFXTextField lastNameField = new JFXTextField();
//        lastNameField.setLabelFloat(true);
//        lastNameField.setPromptText("Last Name");
//        lastNameField.setText(student.getLastName());
//
//        GridPane studentForm = new GridPane();
//        studentForm.setHgap(10);
//
//        ColumnConstraints column1 = new ColumnConstraints();
//        column1.setPercentWidth(50);
//        column1.setHgrow(Priority.SOMETIMES);
//        studentForm.getColumnConstraints().add(column1);
//
//        ColumnConstraints column2 = new ColumnConstraints();
//        column2.setPercentWidth(50);
//        column2.setHgrow(Priority.SOMETIMES);
//        studentForm.getColumnConstraints().add(column2);
//
//        RowConstraints row1 = new RowConstraints();
//        row1.setVgrow(Priority.SOMETIMES);
//        studentForm.getRowConstraints().add(row1);
//
//        studentForm.getChildren().add(firstNameField);
//        GridPane.setColumnIndex(lastNameField,1);
//        studentForm.getChildren().add(lastNameField);
//
//        dialogContent.setHeading(new Text("Edit Student"));
//        dialogContent.setBody(studentForm);
//        JFXDialog dialog = new JFXDialog(mainPane, dialogContent, JFXDialog.DialogTransition.CENTER);
//        JFXButton b = new JFXButton("Okay");
//        b.setOnAction((e) -> {
//            dialog.close();
//        });
//        dialogContent.setActions(b);
//
//
//        JFXButton buttonS = new JFXButton(firstNameS + " " + lastNameS);
//        buttonS.setTextFill(Color.web("#fff"));
//        buttonS.setFont(new Font(20));
//        buttonS.setMinWidth(220);
//        buttonS.setMinHeight(120);
//        buttonS.setOnAction(e -> {
//            dialog.show();
//        });
//
//
//        header.getChildren().add(buttonS);
//
//
//        VBox.setVgrow(header, Priority.ALWAYS);
//        StackPane body = new StackPane();
//        body.setMinHeight(100);
//        VBox content = new VBox();
//        content.getChildren().addAll(header, body);
//        body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");
//
//
//        // create button
//        JFXButton button = new JFXButton("");
//        button.setButtonType(ButtonType.RAISED);
//        button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
//        button.setPrefSize(40, 40);
//        button.setRipplerFill(Color.valueOf(headerColor));
//        button.setScaleX(0);
//        button.setScaleY(0);
//        SVGGlyph glyph = new SVGGlyph(-1,
//                "test",
//                "M5.198,4.256 L7.388,6.769 C7.703,7.083 8.214,7.083 8.531,6.769 L10.72,4.256 C11.034,3.941 11.083,3.38 10.769,3.066 L8.965,3.066 L8.965,1.144 C8.965,0.585 8.532,0.134 7.997,0.134 C7.462,0.134 7.028,0.585 7.028,1.144 L7.028,3.066 L5.247,3.066 C4.932,3.381 4.883,3.94 5.198,4.256 L5.198,4.256 Z"
//                        + "M13.993,1.006 L10.031,1.006 L10.031,1.981 L13.177,1.981 L14.54,9.01 L1.505,9.01 L2.912,1.981 L5.969,1.981 L5.969,1.006 L2.073,1.006 L0.013,9.761 L0.013,13.931 C0.013,15.265 0.485,15.959 1.817,15.959 L14.097,15.959 C15.343,15.959 15.982,15.432 15.982,13.848 L15.982,9.761 L13.993,1.006 L13.993,1.006 Z M10.016,11 L5.985,11 L5.985,9.969 L10.016,9.969 L10.016,11 L10.016,11 Z",
//                Color.WHITE);
//        glyph.setSize(20, 20);
//        button.setGraphic(glyph);
//        button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
//            return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
//        }, header.boundsInParentProperty(), button.heightProperty()));
//        StackPane.setMargin(button, new Insets(0, 12, 0, 0));
//        StackPane.setAlignment(button, Pos.TOP_RIGHT);
//
//        // create button
//        JFXButton button2 = new JFXButton("");
//        button2.setButtonType(ButtonType.RAISED);
//        button2.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
//        button2.setPrefSize(40, 40);
//        button2.setRipplerFill(Color.valueOf(headerColor));
//        button2.setScaleX(0);
//        button2.setScaleY(0);
//        SVGGlyph glyph2 = new SVGGlyph(-1,
//                "test",
//                "M0.982,5.073 L2.007,15.339 C2.007,15.705 2.314,16 2.691,16 L10.271,16 C10.648,16 10.955,15.705 10.955,15.339 L11.98,5.073 L0.982,5.073 L0.982,5.073 Z M7.033,14.068 L5.961,14.068 L5.961,6.989 L7.033,6.989 L7.033,14.068 L7.033,14.068 Z M9.033,14.068 L7.961,14.068 L8.961,6.989 L10.033,6.989 L9.033,14.068 L9.033,14.068 Z M5.033,14.068 L3.961,14.068 L2.961,6.989 L4.033,6.989 L5.033,14.068 L5.033,14.068 Z"
//                        + "M12.075,2.105 L8.937,2.105 L8.937,0.709 C8.937,0.317 8.481,0 8.081,0 L4.986,0 C4.586,0 4.031,0.225 4.031,0.615 L4.031,2.011 L0.886,2.105 C0.485,2.105 0.159,2.421 0.159,2.813 L0.159,3.968 L12.8,3.968 L12.8,2.813 C12.801,2.422 12.477,2.105 12.075,2.105 L12.075,2.105 Z M4.947,1.44 C4.947,1.128 5.298,0.875 5.73,0.875 L7.294,0.875 C7.726,0.875 8.076,1.129 8.076,1.44 L8.076,2.105 L4.946,2.105 L4.946,1.44 L4.947,1.44 Z",
//                Color.WHITE);
//        glyph2.setSize(20, 20);
//        button2.setGraphic(glyph2);
//        button2.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
//            return header.getBoundsInParent().getHeight() - button2.getHeight() / 2;
//        }, header.boundsInParentProperty(), button2.heightProperty()));
//
//        StackPane.setMargin(button2, new Insets(0, 0, 0, 12));
//        StackPane.setAlignment(button2, Pos.TOP_LEFT);
//
//        button2.setOnAction((e) -> {
//            JFXDialogLayout deleteAlertL = new JFXDialogLayout();
//
//
//            deleteAlertL.setHeading(new Text("Are you sure you want to delete student " + firstNameS + " " + lastNameS + "?"));
//            JFXDialog deleteAlert = new JFXDialog(mainPane, deleteAlertL, JFXDialog.DialogTransition.CENTER);
//            JFXButton no = new JFXButton("No");
//            no.setOnAction((ev) -> {
//                deleteAlert.close();
//            });
//            JFXButton yes = new JFXButton("Yes");
//            yes.setOnAction((ev) -> {
//                JFXSnackbar snackbar = new JFXSnackbar(mainPane);
//                EventHandler handler = new EventHandler(){
//                    @Override
//                    public void handle(Event event) {
//                        System.out.println("You clicked");
//                        snackbar.unregisterSnackbarContainer(anchorPane);
//                    }
//                };
//                snackbar.show("You deleted " + firstNameS + " " + lastNameS + ".", "OKAY", 5000, handler);
//                student.delete(Integer.parseInt(button2.getParent().getId()));
//                studentsMasonry.getChildren().remove(child);
//                deleteAlert.close();
//            });
//            deleteAlertL.setActions(no,yes);
//            deleteAlert.show();
//        });
//
//        Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
//                new KeyValue(button.scaleXProperty(),
//                        1,
//                        EASE_BOTH),
//                new KeyValue(button.scaleYProperty(),
//                        1,
//                        EASE_BOTH)));
//        animation.setDelay(Duration.millis(100 * i + 1000));
//        animation.play();
//        Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(240),
//                new KeyValue(button2.scaleXProperty(),
//                        1,
//                        EASE_BOTH),
//                new KeyValue(button2.scaleYProperty(),
//                        1,
//                        EASE_BOTH)));
//        animation2.setDelay(Duration.millis(100 * i + 1000));
//        animation2.play();
//        child.getChildren().addAll(content, button, button2);
//
//    }
//    
//    private String getDefaultColor(int i) {
//        String color = "#FFFFFF";
//        switch (i) {
//        case 0:
//            color = "#8F3F7E";
//            break;
//        case 1:
//            color = "#B5305F";
//            break;
//        case 2:
//            color = "#CE584A";
//            break;
//        case 3:
//            color = "#DB8D5C";
//            break;
//        case 4:
//            color = "#DA854E";
//            break;
//        case 5:
//            color = "#E9AB44";
//            break;
//        case 6:
//            color = "#FEE435";
//            break;
//        case 7:
//            color = "#99C286";
//            break;
//        case 8:
//            color = "#01A05E";
//            break;
//        case 9:
//            color = "#4A8895";
//            break;
//        case 10:
//            color = "#16669B";
//            break;
//        case 11:
//            color = "#2F65A5";
//            break;
//        case 12:
//            color = "#4E6A9C";
//            break;
//        default:
//            break;
//        }
//        return color;
//    }
//    
//}
