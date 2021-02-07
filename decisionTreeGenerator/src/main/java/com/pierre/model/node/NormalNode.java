package com.pierre.model.node;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * An instantiable form of the node class for normal non-terminal nodes.
 * @param <T> Type of dataset.
 */
@Getter
@Setter
public class NormalNode<T> extends Node<T> {

    public NormalNode(Set<T> dataSet) {

        super(dataSet);
    }
}