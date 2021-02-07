package com.pierre.view;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationPane extends BorderPane {

    private Label accuracyLabel;
    private Label accuracyValue;
    private Label errorRateLabel;
    private Label errorRateValue;
    private Label recallLabel;
    private Label recallValue;
    private Label precisionLabel;
    private Label precisionValue;
    private Label specificityLabel;
    private Label specificityValue;
    private Label fScoreLabel;
    private Label fScoreValue;
    private String beta;

    public EvaluationPane() {

        accuracyLabel = new Label("Accuracy:");
        accuracyValue = new Label("0");
        Region accuracyLeftRegion = new Region();
        Region accuracyRightRegion = new Region();
        HBox.setHgrow(accuracyLeftRegion, Priority.ALWAYS);
        HBox.setHgrow(accuracyRightRegion, Priority.ALWAYS);
        HBox accuracyFrame = new HBox(10);
        accuracyFrame.getChildren().addAll(accuracyLeftRegion, accuracyLabel, accuracyValue, accuracyRightRegion);

        errorRateLabel = new Label("Error Rate:");
        errorRateValue = new Label("0");
        Region errorRateLeftRegion = new Region();
        Region errorRateRightRegion = new Region();
        HBox.setHgrow(errorRateLeftRegion, Priority.ALWAYS);
        HBox.setHgrow(errorRateRightRegion, Priority.ALWAYS);
        HBox errorRateFrame = new HBox(10);
        errorRateFrame.getChildren().addAll(errorRateLeftRegion, errorRateLabel, errorRateValue, errorRateRightRegion);

        recallLabel = new Label("Recall:");
        recallValue = new Label("0");
        Region recallLeftRegion = new Region();
        Region recallRightRegion = new Region();
        HBox.setHgrow(recallLeftRegion, Priority.ALWAYS);
        HBox.setHgrow(recallRightRegion, Priority.ALWAYS);
        HBox recallFrame = new HBox(10);
        recallFrame.getChildren().addAll(recallLeftRegion, recallLabel, recallValue, recallRightRegion);

        precisionLabel = new Label("Precision:");
        precisionValue = new Label("0");
        Region precisionLeftRegion = new Region();
        Region precisionRightRegion = new Region();
        HBox.setHgrow(precisionLeftRegion, Priority.ALWAYS);
        HBox.setHgrow(precisionRightRegion, Priority.ALWAYS);
        HBox precisionFrame = new HBox(10);
        precisionFrame.getChildren().addAll(precisionLeftRegion, precisionLabel, precisionValue, precisionRightRegion);

        specificityLabel = new Label("Specificity:");
        specificityValue = new Label("0");
        Region specificityLeftRegion = new Region();
        Region specificityRightRegion = new Region();
        HBox.setHgrow(specificityLeftRegion, Priority.ALWAYS);
        HBox.setHgrow(specificityRightRegion, Priority.ALWAYS);
        HBox specificityFrame = new HBox(10);
        specificityFrame.getChildren().addAll(specificityLeftRegion, specificityLabel, specificityValue,
                specificityRightRegion);

        beta = "1";
        fScoreLabel = new Label("F" + beta + " Score:");
        fScoreValue = new Label("0");
        Region fScoreLeftRegion = new Region();
        Region fScoreRightRegion = new Region();
        HBox.setHgrow(fScoreLeftRegion, Priority.ALWAYS);
        HBox.setHgrow(fScoreRightRegion, Priority.ALWAYS);
        HBox fScoreFrame = new HBox(20);
        fScoreFrame.getChildren().addAll(fScoreLeftRegion, fScoreLabel, fScoreValue, fScoreRightRegion);

        VBox box = new VBox();
        box.getChildren().addAll(accuracyFrame, errorRateFrame, recallFrame, precisionFrame, specificityFrame, fScoreFrame);

        setCenter(box);
        getStyleClass().add("pane");
        getStylesheets().add("styles/evaluation.css");
    }
}
