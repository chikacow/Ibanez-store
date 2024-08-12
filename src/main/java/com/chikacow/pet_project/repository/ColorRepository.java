package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

}
