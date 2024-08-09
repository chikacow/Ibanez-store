package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLineRepository extends JpaRepository<ProductLine,Long> {
    public ProductLine findById(long id);

    public void deleteById(long id);

    public List<ProductLine> findAll();

}
