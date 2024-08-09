package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategory();

    public Category saveCategory(Category category);

    public Category getCategoryById(long id);

    public Category getCategoryByName(String name);

    public void deleteCategory(long id);


}
