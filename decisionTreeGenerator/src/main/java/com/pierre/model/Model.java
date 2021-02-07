package com.pierre.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Main data class. Manages the generation and testing of DAT and contains the training set and testing set.
 * @param <T> Type of dataset
 */
@Getter
@Setter
public class Model<T> {

    private static final List<String> ATTRIBUTES_LIST = new ArrayList<>(Arrays.asList(

            "workplace",
            "gender",
            "location"
    ));

    private static final String TARGET_ATTRIBUTE = "trust";

    private Set<T> trainingSet;
    private DATGenerator<T> generator;
    private Set<T> testingSet;
    private DatasetManager datasetManager;
    private DATTester<T> tester;

    public Model() {

        generator = new DATGenerator<>(TARGET_ATTRIBUTE, ATTRIBUTES_LIST);
        datasetManager = new DatasetManager();
        tester = new DATTester<>();
    }

    public void clearDataset() {

        if (!datasetManager.getDataset().isEmpty()) {

            datasetManager.getDataset().clear();
        }
    }

    public void clearTrainingSet() {

        if (!datasetManager.getTrainingSet().isEmpty()) {

            datasetManager.getTrainingSet().clear();
        }
    }

    public void clearGenerator() {

        generator = new DATGenerator<>(TARGET_ATTRIBUTE, ATTRIBUTES_LIST);
    }

    public void clearTestingSet() {

        if (!datasetManager.getTestingSet().isEmpty()) {

            datasetManager.getTestingSet().clear();
        }
    }

    public void clearTester() {

        tester = new DATTester<>();
    }
}