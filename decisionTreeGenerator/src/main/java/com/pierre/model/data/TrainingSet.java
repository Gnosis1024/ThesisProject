package com.pierre.model.data;

import lombok.Getter;
import lombok.Setter;

/**
 * Pojo of the training set.
 */
@Getter
@Setter
public class TrainingSet {

    private String id;
    private String gender;
    private String workplace;
    private String location;
    private boolean trust;

    public TrainingSet(String id, String gender, String workplace, String location) {

        this.id = id;
        this.gender = gender;
        this.workplace = workplace;
        this.location = location;
    }

    @Override
    public String toString() {

        return id + " " + gender + " " + workplace + " " + location + " " + trust;
    }
}
