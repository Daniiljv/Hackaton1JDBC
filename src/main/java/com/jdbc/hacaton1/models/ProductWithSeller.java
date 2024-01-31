package com.jdbc.hacaton1.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductWithSeller {

private Integer id;
private String category;
private String urlToPhoto;
private String fabric;
private String size;
private String description;
private String usersName;

}
