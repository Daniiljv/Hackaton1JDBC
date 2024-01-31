package com.jdbc.hacaton1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductsFeed {

    private Integer id;
    private String category;
    private String urlToPhoto;
    private String usersName;


}
