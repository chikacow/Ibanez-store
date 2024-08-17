package com.chikacow.pet_project.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDto {

    @NotNull
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull
    @NotEmpty(message = "Add some details")
    private String details;

    private String image;

    private long productId;
}
