package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String bio;

    private String nationality;

    private String band;

    private String image;

    //private Category category;


    //when the product no longer signatured to the artist, it will be removed from the signature list
    //but still exist in the product entity
    @OneToMany(mappedBy = "artist", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SignatureProduct> productList;

    private Category category;


}
