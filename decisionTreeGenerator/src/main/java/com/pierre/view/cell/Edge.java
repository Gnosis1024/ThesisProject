package com.pierre.view.cell;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge<T> extends Group {

    public Edge(Cell<T> source, Cell<T> target) {

        Line line = new Line();

        line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind(target.layoutXProperty().add(target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(target.layoutYProperty().add(target.getBoundsInParent().getHeight() / 2.0));

        line.setFill(Color.WHITE);
        line.setStroke(Color.WHITE);
        line.getStyleClass().add("line");

        getChildren().add(line);
    }
}