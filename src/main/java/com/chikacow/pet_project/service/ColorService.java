package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Color;

import java.util.List;

public interface ColorService {
    public Color saveColor(Color color);

    public Color getColorById(long id);

    public List<Color> getAllColorByProductId(long id);


}
