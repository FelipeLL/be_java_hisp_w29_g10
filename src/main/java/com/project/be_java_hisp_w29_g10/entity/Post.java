package com.project.be_java_hisp_w29_g10.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long publication_id;
    private Integer user_id;
    private String date;
    private Long product_id;
    private Integer category;
    private Double price;
    private Boolean has_promo;
    private Double discount;
}
