package com.pierre.model.node;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Terminal form of a node. Label represents the class. Second constructor for a creating a leaf from a normal node when
 * the original training set is pure.
 * @param <T>
 */
@Getter
@Setter
public class Leaf<T> extends Node<T> {

    public Leaf(Set<T> subset, Label label) {

        super(subset);
        this.label = label;
    }

    public Leaf(Node<T> node, Label label) {

        super(node.getDataset());
        this.label = label;
        setId(node.getId());
    }
}