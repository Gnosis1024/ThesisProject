package com.pierre.model;

import com.pierre.model.data.TrainingSet;
import com.pierre.model.node.Leaf;
import com.pierre.model.node.Node;
import com.pierre.model.node.Node.Label;
import com.pierre.util.AttributeUtil;
import com.pierre.view.alert.AlertManager;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static com.pierre.model.node.Node.Label.*;

/**
 * Tests the DAT using a testing set and calculates evaluation metrics that are then shown through the testing pane.
 * @param <T> Type of dataset
 */
@Getter
@Setter
public class DATTester<T> {

    private int p;
    private int n;
    private int tp;
    private int fp;
    private int tn;
    private int fn;
    private double accuracy;
    private double errorRate;
    private double precision;
    private double recall;
    private double specificity;
    private double fScore;
    private Label result;

    public DATTester() {

        p = 0;
        n = 0;
        tp = 0;
        fp = 0;
        tn = 0;
        fn = 0;
        accuracy = 0;
        errorRate = 0;
        precision = 0;
        recall = 0;
        specificity = 0;
        fScore = 0;
        result = NO;
    }

    /**
     * Iterates over every testing set instance and uses the DAT to compare its label and calculates evaluation metrics
     * accordingly.
     * @param node DAT to be tested against
     * @param testingSet The testing set used
     * @param beta A parameter to calculate the F score
     */
    public void testDAT(Node<T> node, Set<TrainingSet> testingSet, double beta) {

        for (TrainingSet instance : testingSet) {

            testInstance(node, instance);

            if (instance.isTrust()) {

                p++;
            }

            if (result == YES && instance.isTrust()) {

                tp++;
            }

            if (result == NO && !instance.isTrust()) {

                tn++;
            }
        }

        n = testingSet.size() - p;
        fn = p - tp;
        fp = n - tn;

        accuracy = (double) (tp + tn) / (p + n);
        errorRate = 1 - accuracy;
        precision = (double) tp / (tp + fp);
        recall = (double) tp / p;
        specificity = (double) tn / n;
        fScore = ((1 + Math.pow(beta, 2)) * precision * recall) / (Math.pow(beta, 2) * precision + recall);
    }

    /**
     * This's where the actual testing occurs. Here one instance of the testing set is compared to the DAT and the
     * result is saved upon reaching a leaf node. Method checks if current node of the DAT is a leaf and sets the result
     * to the leaf's label. If not a leaf, method iterates over child nodes and calls itself again on the child node
     * with grouping attribute value matching the value of the same attribute in the tested instance.
     * @param node DAT to be tested against
     * @param instance Testing set instance to be tested
     */
    private void testInstance(Node<T> node, TrainingSet instance) {

        if (node instanceof Leaf) {

            result = node.getLabel();

        } else {

            String instanceAttribute = null;

            try {

                instanceAttribute = AttributeUtil.getAttribute(instance, node.getDecisionAttribute()).toString();

            } catch (NullPointerException e) {

                AlertManager.errorAlert("Error Reading Attribute", "Attribute value is null.");
            }

            if (instanceAttribute != null) {

                for (Node<T> childNode : node.getChildNodes()) {

                    String groupingAttributeValue = childNode.getGroupingAttributeValue();

                    if (groupingAttributeValue.equals(instanceAttribute)) {

                        testInstance(childNode, instance);
                    }
                }
            }
        }
    }
}