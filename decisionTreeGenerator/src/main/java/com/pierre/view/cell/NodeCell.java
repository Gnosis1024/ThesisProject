package com.pierre.view.cell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
public class NodeCell<T> extends Cell<T> {

    private Pane decisionPane;
    private Polygon decisionArrow;
    private Text decisionText;

    private Pane informationGainPane;
    private Polygon informationGainLabel;
    private Text informationGainText;

    public NodeCell(int cellId, int layer, Set<T> dataset, String groupingAttribute, String decisionAttribute,
                    Double informationGain, String targetAttribute) {

        super(cellId, layer, dataset, groupingAttribute, targetAttribute);

        double dAWidth = 200;
        double dAHeight = 50;

        double iGLWidth = 100;
        double iGLHeight = 25;

        decisionPane = new Pane();
        decisionArrow = new Polygon(dAWidth/2, dAHeight, dAWidth/4, dAHeight/4,
                dAWidth/3, 0, 2*dAWidth/3, 0, 3*dAWidth/4, dAHeight/4);
        decisionText = new Text(80, 27, decisionAttribute);

        decisionPane.setLayoutX(groupingPane.getLayoutX() - 47);
        decisionPane.setLayoutY(groupingPane.getLayoutY() + 42);

        decisionArrow.getStyleClass().add("arrow");

        decisionText.setFill(Color.WHITE);

        informationGainPane = new Pane();
        informationGainLabel = new Polygon(iGLWidth/2, iGLHeight, iGLWidth/4, iGLHeight/4,
                iGLWidth/3, 0, 2*iGLWidth/3, 0, 3*iGLWidth/4, iGLHeight/4);
        informationGainText = new Text(40, 13, String.format("%.2f", informationGain));

        informationGainPane.setLayoutX(39);
        informationGainPane.setLayoutY(68);

        informationGainLabel.getStyleClass().add("ig-label");

        informationGainText.setFill(Color.WHITE);

        decisionPane.getChildren().addAll(

                decisionArrow,
                decisionText
        );

        informationGainPane.getChildren().addAll(

                informationGainLabel,
                informationGainText
        );

        getChildren().addAll(

                decisionPane,
                informationGainPane
        );

        // Flip order of view

        ObservableList<Node> workingCollection = FXCollections.observableArrayList(getChildren());
        Collections.swap(workingCollection, 0, 2);
        getChildren().setAll(workingCollection);
    }
}