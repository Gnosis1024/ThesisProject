package com.pierre.view.matrix;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Square extends StackPane {

    private Rectangle square;
    private Label label;

    public Square(int x, int y, String value, String labelClass) {

        square = new Rectangle(x, y, 100, 100);
        square.getStyleClass().add("rectangle");

        label = new Label(value);
        label.getStyleClass().add(labelClass);

        getChildren().addAll(square, label);

        getStylesheets().add("styles/matrix.css");
    }
}
