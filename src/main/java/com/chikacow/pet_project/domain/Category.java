package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    //li do can json ignore, still keeping the bidirectional without facing some ridiculous errors
    //even without cascade this list still looking for table in db
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ProductLine> productLineList = new ArrayList<>();


    @Override
    public String toString() {
        return "nothing here";
    }

    //the fukin lombok will implement the fuckin toString() that include the list, which will cause
    //recursion leading to stackoverflow error



    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Artist> artistList;




}
