package com.pierre.view.cell;

import com.pierre.view.DataTable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class Cell<T> extends Pane {

    private int cellId;
    private int layer;

    protected StackPane groupingPane;
    protected Ellipse groupingEllipse;
    protected Text groupingText;

    private DataTable<T> dataTable;
    private Set<T> dataset;
    private String targetAttribute;

    public Cell(int cellId, int layer, Set<T> dataset, String groupingAttribute, String targetAttribute) {

        this.cellId = cellId;
        this.layer = layer;

        this.dataset = dataset;
        this.targetAttribute = targetAttribute;

        groupingPane = new StackPane();
        groupingEllipse = new Ellipse(50, 25, 50, 25);
        groupingText = new Text(50, 25, groupingAttribute);

        groupingEllipse.getStyleClass().add("ellipse-node");
        groupingText.setFill(Color.WHITE);

        dataTable = new DataTable<>();

        dataTable.setData(dataset, 120);

        setTableSize();

        dataTable.stylizeTable(targetAttribute);

        setTableAsPopup();

        groupingPane.getChildren().addAll(

                groupingEllipse,
                groupingText
        );

        getChildren().add(

                groupingPane
        );
    }

    private void setTableAsPopup() {

        Popup popup = new Popup();
        popup.getContent().add(dataTable.getTable());

        setOnMousePressed(event -> popup.show(this, getLayoutX(), getLayoutY()));
        setOnMouseReleased(event -> popup.hide());
    }

    private void setTableSize() {

        dataTable.getTable().setFixedCellSize(25);

        dataTable.getTable().setPrefHeight(25 * (dataTable.getTable().getItems().size() + 1) + 35);
    }

    @Override
    public String toString() {

        return "Cell" + cellId + " " + groupingText.getText();
    }
}