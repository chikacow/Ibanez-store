package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.dto.ProductLineDto;
import com.chikacow.pet_project.repository.ProductLineRepository;
import com.chikacow.pet_project.service.CategoryService;
import com.chikacow.pet_project.service.ProductLineService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/product-line")
public class AdminProdLineController {
    private final ProductLineService productLineService;
    private final CategoryService categoryService;

    @PersistenceContext
    private final EntityManager entityManager;

    private final ProductLineRepository productLineRepository;

    public AdminProdLineController(ProductLineService productLineService, CategoryService categoryService, EntityManager entityManager, ProductLineRepository productLineRepository) {
        this.productLineService = productLineService;
        this.categoryService = categoryService;
        this.entityManager = entityManager;

        this.productLineRepository = productLineRepository;
    }

    @GetMapping
    public String getListProductLine(Model model) {
        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);


        return "admin/product-line/list";
    }

    @GetMapping("/create")
    public String getProductLineCreate(Model model) {
        ProductLineDto productLine = new ProductLineDto();
        model.addAttribute("newProductLine", productLine);

        List<Category> listCate = this.categoryService.getAllCategory();
        model.addAttribute("categoryList", listCate);
        return "admin/product-line/create";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute("newProductLine") ProductLineDto newProductLine) {
        ProductLine productLine = this.productLineService.dtoConvert(newProductLine);

        this.productLineService.saveProductLine(productLine);

//        System.out.println(newProductLine.getCategory() + " from controller");
//        Category category = this.categoryService.getCategoryById(productLine.getCategory().getId());
//        System.out.println(category.getProductLineList());


        return "redirect:/admin/product-line";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable("id") long id) {
        ProductLineDto dto = this.productLineService.convert2Dto(this.productLineService.getByProdLineId(id));
        model.addAttribute("alterProdLine", dto);

        List<Category> listCate = this.categoryService.getAllCategory();
        model.addAttribute("categoryList", listCate);
        //model.addAttribute("cateName", productLine.getCategory().getName());

        return "admin/product-line/update";
    }



    @PostMapping("/update/{id}")
    public String handleUpdateRequest(Model model,
                                @PathVariable("id") long id,
                                @ModelAttribute("alterProdLine") ProductLineDto dto) {


        ProductLine saved = this.productLineService.saveProductLine(this.productLineService.dtoConvert(dto));


        //dont use cascade merge
        //Category category = this.categoryService.getCategoryById(36);
        //loi nay la do cascade
        //System.out.println(category.getProductLineList());
        //System.out.println("from product: " + saved.getCategory().getProductLineList());


        return "redirect:/admin/product-line/update/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteProdLine(@PathVariable("id") long id) {
        this.productLineService.deleteById(id);
        return "redirect:/admin/product-line";
    }

}
