package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.dto.FeatureDto;

import java.util.List;

public interface FeatureService {
    public void saveFeature(Feature feature);

    public List<Feature> getAllFeatureByProductId(long id);

    public FeatureDto convert2Dto(Feature entity);

    public Feature convert2Entity(FeatureDto dto);

}
