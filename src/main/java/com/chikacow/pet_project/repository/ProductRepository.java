package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByName(String name);

}
