package com.pierre.controller;

import com.pierre.model.DATGenerator;
import com.pierre.model.Model;
import com.pierre.model.data.TrainingSet;
import com.pierre.view.View;
import com.pierre.view.alert.AlertManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 * Controller is the link between model and view, it controls the flow of data between the two and manages functionality
 * of UI and data.
 */
@Getter
@Setter
public class Controller {

    private Model<TrainingSet> model;
    private View<TrainingSet> view;
    private Stage primaryStage;

    public Controller(Stage primaryStage) {

        this.model = new Model<>();
        this.view = new View<>();
        this.primaryStage = primaryStage;
    }

    /**
     * Links view and model and manages functionality of buttons.
     */
    public void execute() {

        view.getDatasetItem().addEventHandler(ActionEvent.ACTION, event ->

            view.getDatasetPane().start()
        );

        view.getExitItem().addEventHandler(ActionEvent.ACTION, event ->

                Platform.exit()
        );

        view.getDatasetPane().getImportButton().addEventHandler(ActionEvent.ACTION, event -> {

            model.clearDataset();
            view.clearDataset();

            model.getDatasetManager().importDataset();
            view.getDatasetPane().getDatasetTable().setData(model.getDatasetManager().getDataset(), 60);

            // Checking for and empty dataset.

            if (!model.getDatasetManager().getDataset().isEmpty()) {

                view.getDatasetPane().getElectBox().setDisable(false);
                view.getDatasetPane().getGenerateBox().setDisable(true);
                view.getDatasetPane().getTestingBox().setDisable(true);

            } else {

                AlertManager.errorAlert("Empty Set", "Dataset cannot be empty.");
            }
        });

        view.getDatasetPane().getElectButton().addEventHandler(ActionEvent.ACTION, event -> {

            // Training set can only be elected if its size is less than the size of the dataset and greater than zero
            // and in case of noise the number of noisy instances must not exceed the size of training set.

            if (view.getDatasetPane().getTrainingSetSize() <= model.getDatasetManager().getDataset().size()) {

                if (view.getDatasetPane().getTrainingSetSize() > 0) {

                    if (view.getDatasetPane().getNoiseCheckBox().isSelected()) {

                        if (view.getDatasetPane().getNoiseSize() <= model.getDatasetManager().getTrainingSet().size()) {

                            electTrainingSet();

                        } else {

                            AlertManager.errorAlert("Invalid Number", "Number of noisy instances must " +
                                    "not exceed Training Set size.");
                        }

                    } else {

                        electTrainingSet();
                    }

                } else {

                    AlertManager.errorAlert("Invalid Size", "Training Set size cannot be 0.");
                }

            } else {

                AlertManager.errorAlert("Invalid Size", "Training Set size cannot exceed Dataset size.");
            }
        });

        view.getDatasetPane().getGenerateButton().addEventHandler(ActionEvent.ACTION, event -> {

            int testingSetSize = 0;

            // Parsing the testing set size text field value to decide whether it's a percentage of the training set
            // size or a flat integer and calculating the size accordingly.

            if (view.getDatasetPane().getTestingSizeField().getText() != null &&
                    !view.getDatasetPane().getTestingSizeField().getText().isEmpty()) {

                if (view.getDatasetPane().getTestingSizeField().getText().contains("%")) {

                    int percent = Integer.parseInt(
                            view.getDatasetPane().getTestingSizeField().getText().replaceAll("%", "")
                    );

                    testingSetSize = model.getDatasetManager().getTrainingSet().size() * percent / 100;

                } else {

                    testingSetSize = Integer.parseInt(view.getDatasetPane().getTestingSizeField().getText());
                }
            }

            // Testing set size must be greater than zero and less than training set size. In case the holdout method
            // was selected, training set must be split before generating the DAT.

            if (testingSetSize > 0) {

                if (view.getDatasetPane().getHoldoutCheckBox().isSelected()) {

                    if (testingSetSize < model.getDatasetManager().getTrainingSet().size()) {

                        view.clearTrainingSet();
                        view.clearTestingSet();

                        splitTestingSet(testingSetSize);

                        view.getDatasetPane().getTrainingSetTable().setData(model.getDatasetManager().getTrainingSet(),
                                60);

                        generateDAT();
                        view.getDatasetPane().getTestingBox().setDisable(false);

                    } else {

                        AlertManager.errorAlert("Invalid Number", "Testing Set size must be less than" +
                                "Training Set size.");
                    }

                } else {

                    generateDAT();
                }

            } else {

                AlertManager.errorAlert("Invalid Size", "Testing Set size cannot be 0.");
            }
        });

        view.getDatasetPane().getTestingButton().addEventHandler(ActionEvent.ACTION, event -> {

            model.clearTester();

            view.getTestingPane().getTestingSetTable().setData(model.getTestingSet(), 60);

            model.getTester().testDAT(model.getGenerator().getNode(), model.getTestingSet(),
                    view.getDatasetPane().getBeta());

            transferTestingData();

            view.getTestingPane().start();
        });
    }

    /**
     * Elects training set and fills table view with values.
     */
    private void electTrainingSet() {

        model.clearTrainingSet();
        view.clearTrainingSet();

        model.getDatasetManager().electTrainingSet(
                view.getDatasetPane().getMinimumDegree(),
                view.getDatasetPane().getTrainingSetSize(),
                view.getDatasetPane().getNoiseCheckBox().isSelected(),
                view.getDatasetPane().getNoiseSize()
        );

        model.setTrainingSet(model.getDatasetManager().getTrainingSet());

        view.getDatasetPane().getTrainingSetTable().setData(model.getDatasetManager().getTrainingSet(), 60);

        view.getDatasetPane().getGenerateBox().setDisable(false);
        view.getDatasetPane().getTestingBox().setDisable(true);
    }

    /**
     * Generates DAT according to selected algorithm and adds nodes and edges to graph with proper layout.
     */
    private void generateDAT() {

        model.clearGenerator();
        view.clearGraph();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        model.getGenerator().generateDAT(DATGenerator.Algorithm.ID3, model.getTrainingSet(), view.getGraph());

        view.getGraph().generateEdges(model.getGenerator().getNode());

        view.getGraph().getChildren().addAll(view.getGraph().getEdges());
        view.getGraph().getChildren().addAll(view.getGraph().getCells());

        view.getGraph().setCellsLayout();
    }

    /**
     * Splits training set into testing set and new training set.
     * @param size Size of the testing set.
     */
    private void splitTestingSet(int size) {

        model.clearTestingSet();

        model.clearTester();

        model.getDatasetManager().splitTestingSet(size);

        model.setTestingSet(model.getDatasetManager().getTestingSet());
    }

    /**
     * Formats and transfers evaluation metrics values from the tester to the testing pane.
     **/
    private void transferTestingData() {

        view.getTestingPane().getConfusionMatrix().getTPSquare().getLabel().setText(
                String.valueOf(model.getTester().getTp()));
        view.getTestingPane().getConfusionMatrix().getTNSquare().getLabel().setText(
                String.valueOf(model.getTester().getTn()));
        view.getTestingPane().getConfusionMatrix().getFPSquare().getLabel().setText(
                String.valueOf(model.getTester().getFp()));
        view.getTestingPane().getConfusionMatrix().getFNSquare().getLabel().setText(
                String.valueOf(model.getTester().getFn()));

        view.getTestingPane().getEvaluationPane().getAccuracyValue().setText(
                String.format("%.2f", model.getTester().getAccuracy()));
        view.getTestingPane().getEvaluationPane().getErrorRateValue().setText(
                String.format("%.2f", model.getTester().getErrorRate()));
        view.getTestingPane().getEvaluationPane().getRecallValue().setText(
                String.format("%.2f", model.getTester().getRecall()));
        view.getTestingPane().getEvaluationPane().getPrecisionValue().setText(
                String.format("%.2f", model.getTester().getPrecision()));
        view.getTestingPane().getEvaluationPane().getSpecificityValue().setText(
                String.format("%.2f", model.getTester().getSpecificity()));
        view.getTestingPane().getEvaluationPane().getFScoreValue().setText(
                String.format("%.2f", model.getTester().getFScore()));
        view.getTestingPane().getEvaluationPane().getFScoreLabel().setText(
                "F" + formatDecimal(view.getDatasetPane().getBeta()) + " Score:");
    }

    /**
     * Removes zeros after decimal point in case of integer.
     * @param number The number to be formatted.
     * @return Formatted string of the number.
     */
    private String formatDecimal(double number) {

        if (number == (long) number)

            return String.format("%d", (long) number);

        else

            return String.format("%.1f", number);
    }
}