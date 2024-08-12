package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    public List<Feature> findAllByProductId(long id);
}
