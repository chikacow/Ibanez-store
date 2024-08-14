package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);

}
