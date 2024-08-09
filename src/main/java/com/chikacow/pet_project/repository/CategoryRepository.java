package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAll();

    public Category findById(long id);




    public List<Category> findByName(String name);




}
