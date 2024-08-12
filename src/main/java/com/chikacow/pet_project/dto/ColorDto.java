package com.chikacow.pet_project.dto;

import com.chikacow.pet_project.domain.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto {
    private long id;

    private String colorCode;

    private String name;

    private String alias;



}
