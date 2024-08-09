package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.repository.CategoryRepository;
import com.chikacow.pet_project.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //if there is nothing, then write completely nothing
    @GetMapping
    public String getCategoryList(Model model) {
        List<Category> list = this.categoryService.getAllCategory();
        model.addAttribute("cateList", list);

        return "admin/category/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("newCategory", new Category());
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String postCreateForm(@ModelAttribute("newCategory") Category cate, Model model) {
        //cate.setDescription(cate.getDescription() + "responsed");


        this.categoryService.saveCategory(cate);

        model.addAttribute("newCategory", cate);
        return "redirect:/admin/category/create";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable(name = "id") long id) {
        Category alterCategory = this.categoryService.getCategoryById(id);
        model.addAttribute("alterCategory", alterCategory);
        model.addAttribute("theId",id);

        return "admin/category/update";


    }

    @PostMapping("/update/{id}")
    public String postUpdateForm(Model model,
                                @ModelAttribute("alterCategory") Category alterCategory,
                                @PathVariable(name = "id") long id) {
        //alterCategory.setDescription(alterCategory.getDescription() + "updated");
        model.addAttribute("alterCategory", alterCategory);

        this.categoryService.saveCategory(alterCategory);

        return "admin/category/update";

    }



    @GetMapping("/delete/{id}")
    public String postDeleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    @GetMapping("details/{id}")
    public String getCategoryDetails(Model model,
                                     @PathVariable("id") long id) {

        Category category = this.categoryService.getCategoryById(id);
        model.addAttribute("theCategory", category);
        return "admin/category/details";
    }
}
