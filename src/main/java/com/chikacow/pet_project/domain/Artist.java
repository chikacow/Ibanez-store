package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotEmpty(message = "Artist name?")
    private String name;

    @NotNull
    @NotEmpty(message = "Artist bio?")
    private String bio;

    @NotNull
    @NotEmpty(message = "Artist nationality?")
    private String nationality;

    private String band;

    private String image;

    @NotNull
    @NotEmpty(message = "Artist code?")
    private String signatureModel;

    //private Category category;


    //when the product no longer signatured to the artist, it will be removed from the signature list
    //but still exist in the product entity
    @OneToMany(mappedBy = "artist", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SignatureProduct> productList;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

//    @Override
//    public String toString() {
//        return "hi artist";
//    }

}
