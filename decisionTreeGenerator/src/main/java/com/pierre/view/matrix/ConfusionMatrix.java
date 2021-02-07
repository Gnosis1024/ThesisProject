package com.pierre.view.matrix;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfusionMatrix extends VBox {

    private Label matrix;
    private Label TP;
    private Label TN;
    private Label FP;
    private Label FN;

    private Square TPSquare;
    private Square TNSquare;
    private Square FPSquare;
    private Square FNSquare;
    private HBox PFrame;
    private HBox NFrame;

    public ConfusionMatrix() {

        matrix = new Label("Confusion Matrix");
        matrix.setFont(new Font("Arial", 16));
        matrix.setPadding(new Insets(10, 0, 10, 0));

        TPSquare = new Square(50, 50, "TP", "label-green");
        TNSquare = new Square(100, 100, "TN", "label-green");
        FPSquare = new Square(50, 100, "FP", "label-red");
        FNSquare = new Square(100, 50, "FN", "label-red");

        PFrame = new HBox();
        PFrame.getChildren().addAll(TPSquare, FNSquare);

        NFrame = new HBox();
        NFrame.getChildren().addAll(FPSquare, TNSquare);

        getChildren().addAll(matrix, PFrame, NFrame);
        setAlignment(Pos.TOP_CENTER);
    }
}
