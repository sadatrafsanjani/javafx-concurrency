package com.rafsanjani.concurrency;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    private Service<Void> backgroundTask;

    @FXML
    public void handleButtonAction(ActionEvent event) {

        backgroundTask = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {

                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        for (int i = 0; i < 10000000; i++) {

                            updateMessage("i : " + i);
                        }

                        return null;
                    }
                };
            }
        };
        
        backgroundTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                label.textProperty().unbind();
            }
        });
        
        label.textProperty().bind(backgroundTask.messageProperty());
        backgroundTask.restart();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
