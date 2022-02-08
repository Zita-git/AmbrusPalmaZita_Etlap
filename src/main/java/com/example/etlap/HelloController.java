package com.example.etlap;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
public abstract class HelloController {

    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    protected void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(text);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }

    protected void alertWait(String text) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(text);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    protected boolean confirm(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztos?");
        alert.setHeaderText(text);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }


    protected void hibaKiir(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> alert.show());
            }
        }, 500);
    }
    public static HelloController ujAblak(String fxml, String title, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        HelloController helloController = fxmlLoader.getController();
        helloController.stage = stage;
        return helloController;
    }
}