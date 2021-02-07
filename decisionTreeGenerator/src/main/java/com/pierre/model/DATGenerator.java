package com.pierre.model;

import com.pierre.model.node.Leaf;
import com.pierre.model.node.Node;
import com.pierre.model.node.Node.Label;
import com.pierre.model.node.NormalNode;
import com.pierre.util.AttributeUtil;
import com.pierre.view.Graph;
import com.pierre.view.alert.AlertManager;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class is responsible for using a training set to learn decision rules via an algorithm and producing DAT models. It
 * currently supports two algorithms: ID3 and C4.5. More algorithms can be included and a UI for choosing algorithms can
 * be added as a future development.
 * @param <T> Type of dataset
 */
@Getter
@Setter
public class DATGenerator<T> {

    private String targetAttribute;
    private List<String> attributesList;
    private Node<T> node;

    /**
     * Constructor initializes target attribute and attributes list. Node is initialized with the corresponding training
     * set / subset before the generation process (since algorithm is recursive).
     * @param targetAttribute The name of the target variable (class) of the DAT. Actual value in dataset must be
     *                        boolean since we're dealing with a binary class. Currently hard coded but can be improved
     *                        by allowing UI input from user.
     * @param attributesList List of all attributes within the dataset. Currently hard coded but can be improved by
     *                       allowing UI input from user.
     */
    public DATGenerator(String targetAttribute, List<String> attributesList) {

        this.targetAttribute = targetAttribute;
        this.attributesList = new ArrayList<>(attributesList);
    }

    /**
     * Decides which algorithm will be used to generate DAT.
     * @param algorithm Type of algorithm
     * @param trainingSet Used training set
     * @param graph Output graph of DAT
     */
    public void generateDAT(Algorithm algorithm, Set<T> trainingSet, Graph<T> graph) {

        node = new NormalNode<>(trainingSet);

        switch (algorithm) {

            case ID3:

                generateDAT_ID3(node, trainingSet, graph);
                break;

            case C45:

                generateDAT_C45(node, trainingSet, graph);
                break;

            default:

                AlertManager.errorAlert("Unknown Algorithm", "Selected algorithm is not supported.");
        }
    }

    /**
     * This is an experimental version of the C4.5 algorithm used only to compare results.
     * Space for improvement available.
     * @param node Current node of DAT the algorithm is working on
     * @param dataset Dataset or subset of current node
     * @param graph Output graph of DAT
     */
    private void generateDAT_C45(Node<T> node, Set<T> dataset, Graph<T> graph) {

        final DecisionData decisionData = getDecisionData(dataset, attributesList);
        final String bestAttribute = decisionData.getBestAttribute();
        final double informationGain = decisionData.getInformationGain();

        int layerCount = node.getLayer();

        if (bestAttribute.equals("Unknown")) {

            final Leaf<T> leaf = new Leaf<>(node, getLabel(dataset));

            setGroupingAttributeValue(leaf, dataset, node.getDecisionAttribute());

            attributesList.remove(bestAttribute);

            leaf.setLayer(layerCount);

            node.addChildNode(leaf);

            graph.addCell(leaf, targetAttribute);

        } else {

            node.setDecisionAttribute(bestAttribute);

            node.setInformationGain(informationGain);

            attributesList.remove(bestAttribute);

            final List<Set<T>> splitSets = splitDataset(dataset, bestAttribute);

            graph.addCell(node, targetAttribute);

            for (Set<T> subset : splitSets) {

                if (calculateEntropy(subset) == 0) {

                    final Node<T> leaf = new Leaf<>(subset, getLabel(subset));

                    setGroupingAttributeValue(leaf, subset, bestAttribute);

                    leaf.setLayer(layerCount + 1);

                    node.addChildNode(leaf);

                    graph.addCell(leaf, targetAttribute);

                } else {

                    final Node<T> childNode = new NormalNode<>(subset);

                    setGroupingAttributeValue(childNode, subset, bestAttribute);

                    childNode.setLayer(layerCount + 1);

                    childNode.setDecisionAttribute(bestAttribute);

                    node.addChildNode(childNode);

                    generateDAT_C45(childNode, subset, graph);
                }
            }
        }
    }

    /**
     * Algorithm uses DecisionData to wrap both information gain and best attribute for splitting in a single returned
     * value when performing decision process. Upon each recursive iteration, algorithm increments the layer of the node
     * within the graph. After choosing a decision attribute it is removed from the attributes list and if the dataset
     * is pure a leaf is created from the current node and added to the graph. The dataset is then split into a list of
     * subsets and the algorithm iterates over each subset and calculates its information gain. If the subset is pure,
     * a leaf is created with relevant information values, added as a child node to parent node and added to graph.
     * If it's not, a normal node is created and added as child node and the algorithm is called again on current node.
     * @param node Current node of DAT the algorithm is working on
     * @param trainingSet Training set or subset of current node
     * @param graph Output graph of DAT
     */
    private void generateDAT_ID3(Node<T> node, Set<T> trainingSet, Graph<T> graph) {

        DecisionData decisionData = getDecisionData(trainingSet, attributesList);

        String bestAttribute = decisionData.getBestAttribute();

        double informationGain = decisionData.getInformationGain();

        int layerCount = node.getLayer();

        node.setDecisionAttribute(bestAttribute);

        node.setInformationGain(informationGain);

        attributesList.remove(bestAttribute);

        // This's to check if the original training set is pure so no more iterations are needed and a leaf is created
        // initially representing the whole resulting DAT.

        if (calculateEntropy(node.getDataset()) == 0) {

            node = new Leaf<>(node.getDataset(), getLabel(node.getDataset()));
        }

        graph.addCell(node, targetAttribute);

        if (bestAttribute != null) {

            final List<Set<T>> splitSets = splitDataset(trainingSet, bestAttribute);

            for (Set<T> subset : splitSets) {

                if (calculateEntropy(subset) == 0) {

                    final Node<T> leaf = new Leaf<>(subset, getLabel(subset));

                    setGroupingAttributeValue(leaf, subset, bestAttribute);

                    leaf.setLayer(layerCount + 1);

                    node.addChildNode(leaf);

                    graph.addCell(leaf, targetAttribute);

                } else {

                    final Node<T> childNode = new NormalNode<>(subset);

                    childNode.setLayer(layerCount + 1);

                    childNode.setDecisionAttribute(bestAttribute);

                    setGroupingAttributeValue(childNode, subset, bestAttribute);

                    node.addChildNode(childNode);

                    generateDAT_ID3(childNode, subset, graph);
                }
            }
        }
    }

    /**
     * Method uses reflection to get the value of the node's grouping attribute after a split by knowing which attribute
     * was used to split upon.
     * @param node Node to set its grouping attribute
     * @param trainingSet Subset of the node
     * @param groupingAttribute Name of the attribute used in the split
     */
    private void setGroupingAttributeValue(Node<T> node, Set<T> trainingSet, String groupingAttribute) {

        node.setGroupingAttributeValue(
                AttributeUtil.getAttribute(
                        trainingSet.iterator().next(),
                        groupingAttribute
                ).toString()
        );
    }

    /**
     * Calculates number of positive and negative instances in a leaf's training set and decides the corresponding
     * label of a target attribute. If all negative / positive then it's labeled 'yes' / 'no'. If not pure then label
     * will be 'mixed'. In case target attribute is not a boolean (non-binary class), it'll be labeled 'unknown'.
     * @param trainingSet Training set to calculate its label
     * @return Label of the training set
     */
    private Label getLabel(Set<T> trainingSet) {

        final Object targetAttributeObject = AttributeUtil.getAttribute(trainingSet.iterator().next(), targetAttribute);

        int posCount = 0;
        int negCount = 0;

        // Checking for boolean type for safe cast into boolean.

        if (targetAttributeObject instanceof Boolean) {

            for (T element : trainingSet) {

                if ((Boolean) AttributeUtil.getAttribute(element, targetAttribute)) {

                    posCount++;

                } else {

                    negCount++;
                }
            }

            if (posCount > negCount) {

                return Label.YES;

            } else if (negCount > posCount) {

                return Label.NO;

            } else {

                return Label.MIXED;
            }

        } else {

            System.out.println("Target attribute is not a boolean");
            return Label.UNKNOWN;
        }
    }

    /**
     * Calculates information gain for each attribute in the attributes list for the current training set and chooses
     * the attribute with the highest information gain as the best attribute. Injects values of best attribute and its
     * corresponding information gain into decision data and returns it.
     * @param trainingSet Current training set / subset to calculate its decision data
     * @param attributesList List of attributes to iterate upon for choosing decision data
     * @return Decision data for best attribute and its corresponding information gain
     */
    private DecisionData getDecisionData(Set<T> trainingSet, List<String> attributesList) {

        String bestAttribute = null;
        double informationGain = 0.0;

        for (String attribute : attributesList) {

            double currentInformationGain = calculateInformationGain(trainingSet, attribute);

            if (currentInformationGain > informationGain) {

                bestAttribute = attribute;
                informationGain = currentInformationGain;
            }
        }

        return new DecisionData(bestAttribute, informationGain);
    }

    /**
     * Calculates the entropy of the training set / subset.
     * @param trainingSet Training set / subset to calculate its entropy
     * @return Returns calculated entropy of training set / subset. Since 0log0 is considered 0 when calculating entropy
     * a check for NAN (entropy != entropy) is necessary before returning the entropy value.
     */
    private double calculateEntropy(Set<T> trainingSet) {

        final Object targetAttributeObject = AttributeUtil.getAttribute(trainingSet.iterator().next(), targetAttribute);

        final long subsetCount = trainingSet.size();

        long posCount = 0;

        if (targetAttributeObject instanceof Boolean) {

            for (T element : trainingSet) {

                if ((Boolean) AttributeUtil.getAttribute(element, targetAttribute)) {

                    posCount++;
                }
            }

        } else {

            AlertManager.errorAlert("Attribute Error", "Target Attribute is not a boolean.");
        }

        final double posFraction = (double) posCount / subsetCount;
        final double negFraction = (double) (subsetCount - posCount) / subsetCount;

        final double entropy = -negFraction * Math.log(negFraction) / Math.log(2)
                - posFraction * Math.log(posFraction) / Math.log(2);

        return entropy != entropy ? 0.0 : entropy;
    }

    /**
     * Calculates information gain of a training set / subset after splitting it according to an attribute.
     * @param trainingSet Training set / subset to calculate its information gain
     * @param splitAttribute Attribute to split the training set / subset upon
     * @return Calculated information gain
     */
    private double calculateInformationGain(Set<T> trainingSet, String splitAttribute) {

        final List<Set<T>> subsets = splitDataset(trainingSet, splitAttribute);

        double informationGain = calculateEntropy(trainingSet);

        for (Set<T> subset : subsets) {

            informationGain -= ((double) subset.size() / trainingSet.size()) * calculateEntropy(subset);
        }

        return informationGain;
    }

    /**
     * Uses Java Stream to transform a training set / subset to a map with a key set of the values of split attribute
     * and a value collection of the corresponding subset after the split and returns the value collection as list of
     * subsets.
     * @param trainingSet Training set / subset to be split
     * @param splitAttribute Attribute to be split upon
     * @return List of subsets where each subset contains a different value of the split attribute
     */
    private List<Set<T>> splitDataset(Set<T> trainingSet, String splitAttribute) {

        return new ArrayList<>(
                trainingSet
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        element -> AttributeUtil.getAttribute(element, splitAttribute),
                                        Collectors.toSet()
                                )
                        )
                        .values()
        );
    }

    @Getter
    @Setter
    public static class DecisionData {

        private String bestAttribute;
        private double informationGain;

        public DecisionData(String bestAttribute, double informationGain) {

            this.bestAttribute = bestAttribute;
            this.informationGain = informationGain;
        }
    }

    public enum Algorithm {

        ID3, C45
    }
}