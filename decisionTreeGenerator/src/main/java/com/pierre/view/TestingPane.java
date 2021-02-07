package com.pierre.view;

import com.pierre.model.data.TrainingSet;
import com.pierre.view.matrix.ConfusionMatrix;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestingPane extends HBox {

    private ConfusionMatrix confusionMatrix;
    private EvaluationPane evaluationPane;
    private Label tableLabel;
    private DataTable<TrainingSet> testingSetTable;

    public TestingPane() {

        confusionMatrix = new ConfusionMatrix();
        evaluationPane = new EvaluationPane();

        tableLabel = new Label("Testing Set");
        tableLabel.setFont(new Font("Arial", 16));
        tableLabel.setPadding(new Insets(10, 0, 10, 0));
        testingSetTable = new DataTable<>();
        testingSetTable.getTable().setPrefSize(400, 370);

        VBox evaluationBox = new VBox();
        evaluationBox.getChildren().addAll(confusionMatrix, evaluationPane);

        VBox testingSetBox = new VBox();
        testingSetBox.getChildren().addAll(tableLabel, testingSetTable.getTable());
        testingSetBox.setAlignment(Pos.TOP_CENTER);

        getChildren().addAll(evaluationBox, testingSetBox);
    }

    public void start() {

        Scene scene;

        if (this.getScene() == null) {

            scene = new Scene(this);

        } else {

            scene = this.getScene();
        }

        Stage stage = new Stage();
        stage.setTitle("Testing Panel");
        stage.getIcons().add(new Image("icons/test.png"));
        stage.setScene(scene);
        stage.show();
    }
}
