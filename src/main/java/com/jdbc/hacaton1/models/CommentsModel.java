package com.jdbc.hacaton1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommentsModel {

    private Integer id;
    private Integer productId;
    private Integer userId;
    private String comment;
    private Integer likesCount;
    private Integer dislikesCount;

}
