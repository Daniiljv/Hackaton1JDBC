package com.jdbc.hacaton1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MineProducts {

    private Integer id;
    private String category;
    private String urlPhoto;
    private String fabric;
    private String size;
    private String description;
    private Double avgEvaluation;
    private Integer countOfComments;

}