package com.pierre.model.node;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Abstract form of a DAT's node. Each node in the DAT contains a dataset, a list of child nodes, and id, a layer for
 * graph layout and various values and statistics like information gain and decision attribute.
 * @param <T> Type of dataset.
 */
@Getter
@Setter
public abstract class Node<T> {

    private static int count = 0;
    private int id;
    private int layer;
    private Set<T> dataset;
    private List<Node<T>> childNodes;
    private String decisionAttribute;
    private String groupingAttributeValue;
    protected Label label;
    private double informationGain;

    public Node(Set<T> dataset) {

        this.dataset = dataset;

        id = count++;
        layer = 0;
        childNodes = new ArrayList<>();
        groupingAttributeValue = "Root Node";
    }

    public void addChildNode(Node<T> childNode) {

        childNodes.add(childNode);
    }

    @Override
    public String toString() {

        return id + " DecAtt: " + decisionAttribute + " GrpAtt: " + groupingAttributeValue;
    }

    public enum Label {

        YES("yes"), NO("no"), MIXED("mixed"), UNKNOWN("unknown");

        private String name;

        Label(String name) {

            this.name = name;
        }

        @Override
        public String toString() {

            return name;
        }
    }
}