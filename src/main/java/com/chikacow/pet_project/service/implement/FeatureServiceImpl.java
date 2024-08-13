package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.dto.FeatureDto;
import com.chikacow.pet_project.repository.FeatureRepository;
import com.chikacow.pet_project.service.FeatureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureServiceImpl implements FeatureService {
    private final ModelMapper modelMapper;
    private final FeatureRepository featureRepository;

    public FeatureServiceImpl(ModelMapper modelMapper, FeatureRepository featureRepository) {
        this.modelMapper = modelMapper;
        this.featureRepository = featureRepository;
    }

    @Override
    public void saveFeature(Feature feature) {
        this.featureRepository.save(feature);
    }

    @Override
    public List<Feature> getAllFeatureByProductId(long id) {
        return this.featureRepository.findAllByProductId(id);
    }

    @Override
    public Feature getFeatureById(long id) {
        Optional<Feature> container = this.featureRepository.findById(id);
        if (container.isEmpty()) {
            return null;
        }
        return container.get();
    }

    @Override
    public void deleteFeatureById(long id) {
        this.featureRepository.deleteById(id);
    }

    @Override
    public FeatureDto convert2Dto(Feature entity) {
        //no complex handing
        return modelMapper.map(entity, FeatureDto.class);
    }

    @Override
    public Feature convert2Entity(FeatureDto dto) {
        return modelMapper.map(dto, Feature.class);
    }
}
