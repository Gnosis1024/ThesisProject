package com.pierre.model.data;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO of the dataset.
 */
@Getter
@Setter
public class Dataset {

    private String id;
    private String gender;
    private String workplace;
    private String location;
    private String degree;

    public Dataset(String id, String gender, String workplace, String location, String degree) {

        this.id = id;
        this.gender = gender;
        this.workplace = workplace;
        this.location = location;
        this.degree = degree;
    }
}
