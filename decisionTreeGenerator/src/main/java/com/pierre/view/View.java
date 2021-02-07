package com.pierre.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class View<T> extends BorderPane {

    private static final double WIDTH = 1024;
    private static final double HEIGHT = 768;

    private Graph<T> graph;
    private DatasetPane datasetPane;
    private TestingPane testingPane;
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem datasetItem;
    private MenuItem exitItem;

    public View() {

        graph = new Graph<>(WIDTH, HEIGHT);
        testingPane = new TestingPane();
        datasetPane = new DatasetPane();

        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        datasetItem = new MenuItem("Dataset");
        exitItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(datasetItem, exitItem);

        menuBar.getMenus().add(fileMenu);

        setTop(menuBar);
        setRight(graph);

        setPrefSize(WIDTH, HEIGHT);
    }

    public void clearDataset() {

        datasetPane.getDatasetTable().getTable().getColumns().clear();
        datasetPane.getDatasetTable().getTable().getItems().clear();
    }

    public void clearTrainingSet() {

        datasetPane.getTrainingSetTable().getTable().getColumns().clear();
        datasetPane.getTrainingSetTable().getTable().getItems().clear();
    }

    public void clearGraph() {

        graph.getCells().clear();
        graph.getEdges().clear();
        graph.getCellMap().clear();
        graph.getChildren().clear();
    }

    public void clearTestingSet() {

        testingPane.getTestingSetTable().getTable().getColumns().clear();
        testingPane.getTestingSetTable().getTable().getItems().clear();
    }
}
