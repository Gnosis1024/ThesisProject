package com.pierre.model;

import com.pierre.model.data.Dataset;
import com.pierre.model.data.TrainingSet;
import com.pierre.view.alert.AlertManager;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Responsible for importing and creating the dataset, training set and testing set.
 * Future upgrade suggestion: Class can be made generic by removing hardcoded attributes values and creating a UI for
 * the user to fill them, import them from an external file or searching the dataset for values (can be expensive is
 * case of large dataset).
 */
@Getter
@Setter
public class DatasetManager {

    private static final String STARBUCKS = "Starbucks";
    private static final String IKEA = "Ikea";
    private static final String GOOGLE = "Google";
    private static final String FEMALE = "female";
    private static final String LEEDS = "Leeds";

    private Set<Dataset> dataset;
    private Set<TrainingSet> trainingSet;
    private Set<TrainingSet> testingSet;

    public DatasetManager() {

        dataset = new HashSet<>();
        trainingSet = new HashSet<>();
        testingSet = new HashSet<>();
    }

    /**
     * Reads data from CSV file and parses it into the dataset by adding each line as an instance.
     */
    public void importDataset() {

        String csvFile = "data/dataset.csv";
        String line;
        Set<List<String>> importedData = new HashSet<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource(csvFile)).getFile()))) {

            while ((line = br.readLine()) != null) {

                importedData.add(Arrays.asList(line.split(",")));
            }

        } catch (IOException e) {

            AlertManager.errorAlert("Error Reading File", "Dataset file is corrupt or unreadable.");
        }

        for (List<String> row : importedData) {

            dataset.add(new Dataset(

                    row.get(0),
                    row.get(1),
                    row.get(2),
                    row.get(3),
                    row.get(4)
            ));
        }
    }

    /**
     * Elects training set from dataset and labels it according to boolean formula. Also adds noise to training set by
     * negating the label value and adding a '*' to the instance's id to mark it.
     * Future upgrade suggestion: Current version can duplicate dataset instances in the training set to finish the
     * while loop, a check for an exception can be added with removing instances from the dataset list with each
     * iteration to overcome this issue.
     * @param minimumDegree Minimum degree of elected dataset instance.
     * @param size Size of the training set.
     * @param noise Adds noise if true.
     * @param noiseSize Number of added noisy instances.
     */
    public void electTrainingSet(int minimumDegree, int size, boolean noise, int noiseSize) {
        
        List<Dataset> dataList = new ArrayList<>(dataset);

        while (trainingSet.size() < size) {

            Collections.shuffle(dataList);
            Dataset datasetInstance = dataList.get(0);

            String id = datasetInstance.getId();
            String gender = datasetInstance.getGender();
            String workplace = datasetInstance.getWorkplace();
            String location = datasetInstance.getLocation();
            int degree = Integer.parseInt(datasetInstance.getDegree());

            if (degree >= minimumDegree) {

                trainingSet.add(new TrainingSet(id, gender, workplace, location));
            }
        }

        trainingSet.forEach(e -> e.setTrust(
                        !(e.getWorkplace().equals(STARBUCKS) ||
                        (e.getWorkplace().equals(IKEA) && e.getGender().equals(FEMALE)) ||
                        (e.getWorkplace().equals(GOOGLE) && e.getLocation().equals(LEEDS)))
        ));

        if (noise) {

            List<TrainingSet> trainingList = new ArrayList<>(trainingSet);

            while (noiseSize > 0) {

                Collections.shuffle(trainingList);
                TrainingSet trainingInstance = trainingList.get(0);

                if (!trainingInstance.getId().contains("*")) {

                    trainingInstance.setTrust(!trainingInstance.isTrust());
                    trainingInstance.setId("* " + trainingInstance.getId());
                    noiseSize--;
                }
            }
        }
    }

    /**
     * Removes instances from training set and adds them to testing set until the size is met.
     * @param size Size of the testing set.
     */
    public void splitTestingSet(int size) {

        List<TrainingSet> trainingList = new ArrayList<>(trainingSet);

        while (testingSet.size() < size) {

            Collections.shuffle(trainingList);
            TrainingSet instance = trainingList.get(0);

            testingSet.add(instance);
            trainingSet.remove(instance);
        }
    }
}