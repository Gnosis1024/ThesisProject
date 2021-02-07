package com.pierre.view;

import com.pierre.model.data.Dataset;
import com.pierre.model.data.TrainingSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@Getter
@Setter
public class DatasetPane extends BorderPane {

    private Button importButton;

    private Button electButton;
    private Label minDegreeLabel;
    private Label trainingSizeLabel;
    private TextField minDegreeField;
    private TextField trainingSizeField;
    private CheckBox noiseCheckBox;
    private Label noiseCheckLabel;
    private Label noiseSizeLabel;
    private TextField noiseSizeField;
    private VBox electBox;

    private Button generateButton;
    private CheckBox holdoutCheckBox;
    private Label holdoutLabel;
    private Label testingSizeLabel;
    private TextField testingSizeField;
    private VBox generateBox;

    private Button testingButton;
    private Label betaLabel;
    private TextField betaField;
    private VBox testingBox;

    private VBox controlBox;

    private Label datasetLabel;
    private DataTable<Dataset> datasetTable;
    private Label trainingSetLabel;
    private DataTable<TrainingSet> trainingSetTable;

    private HBox dataBox;

    public DatasetPane() {

        importButton = new Button("Import Dataset");

        electButton = new Button("Elect Training Set");
        minDegreeLabel = new Label("Minimum Degree:");
        trainingSizeLabel = new Label("Training Set Size:");
        minDegreeField = new TextField();
        trainingSizeField = new TextField();
        noiseCheckBox = new CheckBox();
        noiseCheckLabel = new Label("Add Noise");
        noiseSizeLabel = new Label("Noisy Instances:");
        noiseSizeField = new TextField();
        electBox = new VBox(5);

        generateButton = new Button("Generate DAT");
        holdoutCheckBox = new CheckBox();
        holdoutLabel = new Label("Use Holdout Method");
        testingSizeLabel = new Label("Testing Set Size:");
        testingSizeField = new TextField();
        generateBox = new VBox(5);

        testingButton = new Button("Evaluate DAT");
        betaLabel = new Label("Beta:");
        betaField = new TextField();
        testingBox = new VBox(5);

        // Election Configuration /////////////////////////

        minDegreeField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                minDegreeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        minDegreeField.setText("25");
        minDegreeField.setPrefWidth(50);

        trainingSizeField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                trainingSizeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        trainingSizeField.setText("20");
        trainingSizeField.setPrefWidth(50);

        HBox minDegreeBox = new HBox();
        Region minDegreeRegion = new Region();
        HBox.setHgrow(minDegreeRegion, Priority.ALWAYS);
        minDegreeBox.getChildren().addAll(minDegreeLabel, minDegreeRegion, minDegreeField);

        HBox sizeBox = new HBox();
        Region sizeRegion = new Region();
        HBox.setHgrow(sizeRegion, Priority.ALWAYS);
        sizeBox.getChildren().addAll(trainingSizeLabel, sizeRegion, trainingSizeField);

        noiseSizeField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                noiseSizeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        noiseSizeField.setText("2");
        noiseSizeField.setPrefWidth(50);

        HBox noiseCheckHBox = new HBox();
        noiseCheckHBox.getChildren().addAll(noiseCheckBox, noiseCheckLabel);

        HBox noiseSizeBox = new HBox();
        Region noiseSizeRegion = new Region();
        HBox.setHgrow(noiseSizeRegion, Priority.ALWAYS);
        noiseSizeBox.getChildren().addAll(noiseSizeLabel, noiseSizeRegion, noiseSizeField);

        noiseSizeBox.setDisable(true);

        noiseCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->

                noiseSizeBox.setDisable(!newValue)
        );

        electBox.getChildren().addAll(electButton, sizeBox, minDegreeBox, noiseCheckHBox, noiseSizeBox);

        electBox.setDisable(true);

        // Generation Configuration /////////////////////////

        HBox holdoutBox = new HBox();
        holdoutBox.getChildren().addAll(holdoutCheckBox, holdoutLabel);

        testingSizeField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("^[0]*?(?<Percentage>[1-9][0-9]?|100)%?$")) {
                testingSizeField.setText("");
            }
        });

        testingSizeField.setText("33%");
        testingSizeField.setPrefWidth(50);

        HBox testingSizeBox = new HBox();
        Region testingSizeRegion = new Region();
        HBox.setHgrow(testingSizeRegion, Priority.ALWAYS);
        testingSizeBox.getChildren().addAll(testingSizeLabel, testingSizeRegion, testingSizeField);
        testingSizeBox.setDisable(true);

        holdoutCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                testingSizeBox.setDisable(!newValue));

        generateBox.getChildren().addAll(generateButton, holdoutBox, testingSizeBox);
        generateBox.setDisable(true);

        // Testing Configuration /////////////////////////

        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter<UnaryOperator<TextFormatter.Change>> formatter = new TextFormatter<>( change ->
                pattern.matcher(change.getControlNewText()).matches() ? change : null);

        betaField.setTextFormatter(formatter);

        betaField.setText("1");
        betaField.setPrefWidth(50);

        HBox betaBox = new HBox();
        Region betaRegion = new Region();
        HBox.setHgrow(betaRegion, Priority.ALWAYS);
        betaBox.getChildren().addAll(betaLabel, betaRegion, betaField);

        testingBox.getChildren().addAll(testingButton, betaBox);
        testingBox.setDisable(true);

        // Main Configuration /////////////////////////

        LabeledSeparator datasetSeparator = new LabeledSeparator("Dataset", 135);
        LabeledSeparator trainingSetSeparator = new LabeledSeparator("Training Set", 110);
        LabeledSeparator generatorSeparator = new LabeledSeparator("DAT", 145);
        LabeledSeparator testingSeparator = new LabeledSeparator("Testing", 145);

        controlBox = new VBox(20);
        controlBox.getChildren().addAll(datasetSeparator, importButton, trainingSetSeparator,
                electBox, generatorSeparator, generateBox, testingSeparator, testingBox);

        String cssLayout =  "-fx-border-color: CCCCCC;\n" +
                            "-fx-border-insets: 1;\n" +
                            "-fx-border-width: 1;\n" +
                            "-fx-border-style: solid;\n";

        controlBox.setStyle(cssLayout);

        VBox datasetBox = new VBox(10);
        datasetLabel = new Label("Dataset");
        datasetLabel.setFont(new Font("Arial", 16));
        datasetLabel.setPadding(new Insets(10, 0, 0, 0));
        datasetTable = new DataTable<>();
        datasetTable.getTable().setPrefSize(370, 450);
        datasetBox.getChildren().addAll(datasetLabel, datasetTable.getTable());
        datasetBox.setAlignment(Pos.TOP_CENTER);

        VBox trainingSetBox = new VBox(10);
        trainingSetLabel = new Label("Training Set");
        trainingSetLabel.setFont(new Font("Arial", 16));
        trainingSetLabel.setPadding(new Insets(10, 0, 0, 0));
        trainingSetTable = new DataTable<>();
        trainingSetTable.getTable().setPrefSize(370, 450);
        trainingSetBox.getChildren().addAll(trainingSetLabel, trainingSetTable.getTable());
        trainingSetBox.setAlignment(Pos.TOP_CENTER);

        dataBox = new HBox(10);
        dataBox.getChildren().addAll(controlBox, datasetBox, trainingSetBox);

        setLeft(dataBox);

        setPrefSize(950, 500);
    }

    public void start() {

        Scene scene;

        if (this.getScene() == null) {

            scene = new Scene(this);

        } else {

            scene = this.getScene();
        }

        Stage stage = new Stage();
        stage.setTitle("Dataset Manager");
        stage.getIcons().add(new Image("icons/dataset.png"));
        stage.setScene(scene);
        stage.show();
    }

    public int getMinimumDegree() {

        if (minDegreeField.getText() == null || minDegreeField.getText().isEmpty()) {

            return 0;

        } else {

            return Integer.parseInt(minDegreeField.getText());
        }
    }

    public int getTrainingSetSize() {

        if (trainingSizeField.getText() == null || trainingSizeField.getText().isEmpty()) {

            return 0;

        } else {

            return Integer.parseInt(trainingSizeField.getText());
        }
    }

    public int getNoiseSize() {

        if (noiseSizeField.getText() == null || noiseSizeField.getText().isEmpty()) {

            return 0;

        } else {

            return Integer.parseInt(noiseSizeField.getText());
        }
    }

    public double getBeta() {

        if (betaField.getText() == null || betaField.getText().isEmpty()) {

            return 0;

        } else {

            return Double.parseDouble(betaField.getText());
        }
    }
}