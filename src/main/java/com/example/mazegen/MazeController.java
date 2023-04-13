package com.example.mazegen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MazeController implements Initializable {
    @FXML
    private Button logoutB;
    @FXML
    private AnchorPane mazePanel;
    @FXML
    private Spinner<Integer> myS;
    @FXML
    private AnchorPane panel2;

    public int size;
    private double width_, height_;

    public void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK if you want to exit");

        if(alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) mazePanel.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 50);
        spinnerValueFactory.setValue(5);
        myS.setValueFactory(spinnerValueFactory);

        size = myS.getValue();

        myS.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                size = myS.getValue();
            }
        });
    }

    public void startButton(ActionEvent e) throws InterruptedException {
        width_ = panel2.getWidth();
        height_ = panel2.getHeight();
        double m_wid = Math.floor((width_ - 40) / size);
        double m_hei = Math.floor((height_ - 40) / size);

        Generator gen = new Generator(size, (int)m_wid, (int)m_hei);
        Canvas canvas = new Canvas(panel2.getWidth(), panel2.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        panel2.getChildren().clear();
        panel2.getChildren().add(gc.getCanvas());

        int X = 20;
        int Y = 20;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Point2D p1 = new Point2D(X, Y);
                Point2D p2 = new Point2D(X, Y+(int)m_hei);
                Point2D p3 = new Point2D(X+(int)m_wid, Y+(int)m_hei);
                Point2D p4 = new Point2D(X+(int)m_wid, Y);
                gc.setLineWidth(1.0);
                gc.setStroke(Color.WHITE);
                if(gen.maze[i][j].get_left()) {
                    gc.strokeLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
                }
                if(gen.maze[i][j].get_down()) {
                    gc.strokeLine(p2.getX(),p2.getY(),p3.getX(),p3.getY());
                }
                if(gen.maze[i][j].get_right()) {
                    gc.strokeLine(p3.getX(),p3.getY(),p4.getX(),p4.getY());
                }
                if(gen.maze[i][j].get_up()) {
                    gc.strokeLine(p4.getX(),p4.getY(),p1.getX(),p1.getY());
                }

                X+=(int)m_wid;
            }
            X = 20;
            Y += (int)m_hei;


        }

        Thread.sleep(100);
        gen.DFS(gen.maze[0][0], size, gc, panel2, (int)m_wid, (int)m_hei);


    }

    public void resetButton(ActionEvent e) {
        panel2.getChildren().clear();
    }
}

