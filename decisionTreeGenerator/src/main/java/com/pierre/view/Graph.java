package com.pierre.view;

import com.pierre.model.node.Leaf;
import com.pierre.model.node.Node;
import com.pierre.model.node.NormalNode;
import com.pierre.view.cell.Cell;
import com.pierre.view.cell.Edge;
import com.pierre.view.cell.LeafCell;
import com.pierre.view.cell.NodeCell;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Graph<T> extends Pane {

    private double graphWidth;

    private double graphHeight;

    private List<Cell<T>> cells;

    private List<Edge<T>> edges;

    private Map<Integer, Cell<T>> cellMap;

    public Graph(double graphWidth, double graphHeight) {

        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;

        cells = new ArrayList<>();
        edges = new ArrayList<>();
        cellMap = new HashMap<>();

        setPrefSize(graphWidth, graphHeight);
    }

    public void addCell(Node<T> node, String targetAttribute) {

        final Cell<T> cell;

        if (node instanceof NormalNode) {

            cell = new NodeCell<>(node.getId(), node.getLayer(), node.getDataset(),
                    node.getGroupingAttributeValue(), node.getDecisionAttribute(),
                    node.getInformationGain(), targetAttribute);

        } else if (node instanceof Leaf) {

            cell = new LeafCell<>(node.getId(), node.getLayer(), node.getDataset(),
                    node.getGroupingAttributeValue(), node.getLabel(), targetAttribute);

        } else {

            throw new UnsupportedOperationException("Unsupported type: " + node.getClass().toString());
        }

        cells.add(cell);
        cellMap.put(cell.getCellId(), cell);
    }

    public void generateEdges(Node<T> node) {

        for (Node<T> childNode : node.getChildNodes()) {

            addEdge(node.getId(), childNode.getId());
            generateEdges(childNode);
        }
    }

    private void addEdge(int sourceId, int targetId) {

        Cell<T> sourceCell = cellMap.get(sourceId);
        Cell<T> targetCell = cellMap.get(targetId);

        if (sourceCell != null && targetCell != null) {

            Edge<T> edge = new Edge<>(sourceCell, targetCell);
            edges.add(edge);
        }
    }

    public void setCellsLayout() {

        Map<Integer, List<Cell<T>>> layerMap = cells.stream().collect(Collectors.groupingBy(Cell::getLayer));

        double horizontalSpacing;
        double verticalSpacing;

        double x;
        double y;

        for (List<Cell<T>> cellList : layerMap.values()) {

            horizontalSpacing = graphWidth / (cellList.size() + 1);

            x = horizontalSpacing;

            for (Cell<T> cell : cellList) {

                cell.setLayoutX(x);

                x += horizontalSpacing;
            }
        }

        verticalSpacing = graphHeight / (layerMap.keySet().size() + 1);

        y = verticalSpacing;

        for (Integer layer : layerMap.keySet()) {

            for (Cell<T> cell : layerMap.get(layer)) {

                cell.setLayoutY(y);
            }

            y += verticalSpacing;
        }
    }
}