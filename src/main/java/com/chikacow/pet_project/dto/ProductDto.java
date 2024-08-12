package com.chikacow.pet_project.dto;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Feature;

import java.util.List;

public class ProductDto {

    private long id;

    private String name;

    private String description;


    private long productLineId;

    private String mainImage;


    private List<Feature> features;


    private List<Color> colors;

}
