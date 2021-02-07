package com.pierre.view.cell;

import com.pierre.model.node.Node.Label;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class LeafCell<T> extends Cell<T> {

    public LeafCell(int cellId, int layer, Set<T> dataset, String groupingAttribute,
                    Label label, String targetAttribute) {

        super(cellId, layer, dataset, groupingAttribute, targetAttribute);

        switch (label) {

            case YES:

                groupingEllipse.getStyleClass().add("ellipse-leaf-yes");
                groupingText.setFill(Color.BLACK);
                break;

            case NO:

                groupingEllipse.getStyleClass().add("ellipse-leaf-no");
                groupingText.setFill(Color.WHITE);
                break;

            case MIXED:

                groupingEllipse.getStyleClass().add("ellipse-leaf-mixed");
                groupingText.setFill(Color.BLACK);
                break;

            case UNKNOWN:

                groupingEllipse.getStyleClass().add("ellipse-leaf-unknown");
                groupingText.setFill(Color.WHITE);
                break;
        }
    }
}