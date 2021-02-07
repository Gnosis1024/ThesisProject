package com.pierre.application;

import com.pierre.controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String... args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Controller controller = new Controller(primaryStage);

        controller.execute();

        Scene scene = new Scene(controller.getView());
        scene.getStylesheets().add("styles/root.css");

        primaryStage.setTitle("DAT Generator");
        primaryStage.getIcons().add(new Image("icons/tree.png"));

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }
}