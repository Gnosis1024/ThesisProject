package com.pierre.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabeledSeparator extends HBox {

    private Label label;
    private Separator leftSeparator;
    private Separator rightSeparator;

    public LabeledSeparator(String text, int width) {

        label = new Label(text);
        leftSeparator = new Separator();
        rightSeparator = new Separator();
        rightSeparator.setMinWidth(width);
        getChildren().addAll(leftSeparator, label, rightSeparator);
        setAlignment(Pos.CENTER_LEFT);
    }
}
