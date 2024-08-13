package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    //li do can json ignore, still keeping the bidirectional without facing some ridiculous errors
    //even without cascade this list still looking for table in db
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ProductLine> productLineList = new ArrayList<>();


    @Override
    public String toString() {
        return "nothing here";
    }

    //the fukin lombok will implement the fuckin toString() that include the list, which will cause
    //recursion leading to stackoverflow error


    private List<Artist> artistList;




}
