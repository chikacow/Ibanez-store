package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        List<Category> list = this.categoryRepository.findByName(name);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void deleteCategory(long id) {
        this.categoryRepository.deleteById(id);
    }


}
