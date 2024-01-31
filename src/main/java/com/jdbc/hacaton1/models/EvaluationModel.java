package com.jdbc.hacaton1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EvaluationModel {

private Integer id;
private Integer productId;
private Integer userId;
private Integer evaluate;

}
