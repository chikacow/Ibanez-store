package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.dto.ProductLineDto;
import com.chikacow.pet_project.repository.ProductLineRepository;
import com.chikacow.pet_project.service.CategoryService;
import com.chikacow.pet_project.service.ProductLineService;
import com.chikacow.pet_project.service.SimpleFileService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

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

    private final SimpleFileService simpleFileService;


    //spring dont allow to use this like this, must pass it directly into the method params
    //private final RedirectAttributes redirectAttributes;

    public AdminProdLineController(ProductLineService productLineService, CategoryService categoryService, EntityManager entityManager, ProductLineRepository productLineRepository, SimpleFileService simpleFileService) {
        this.productLineService = productLineService;
        this.categoryService = categoryService;
        this.entityManager = entityManager;

        this.productLineRepository = productLineRepository;

        this.simpleFileService = simpleFileService;

    }

    @GetMapping
    public String getListProductLine(Model model) {

        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);


        return "admin/product-line/list";
    }

    @GetMapping("/create")
    public String getProductLineCreate(Model model, RedirectAttributes redirectAttributes) {

        if (this.categoryService.getAllCategory().isEmpty()) {
            String message = "Must create category first";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/admin/category/create";

        }
        System.out.println();
        if (!model.containsAttribute("newProductLine")) {

            model.addAttribute("newProductLine", new ProductLineDto());
        }

        System.out.println(model.toString());
        //Neu tao moi duoi nay thi se bi ghi de ban ghi chua bindingresult
//        ProductLineDto productLine = new ProductLineDto();
//        model.addAttribute("newProductLine", productLine);

        List<Category> listCate = this.categoryService.getAllCategory();
        model.addAttribute("categoryList", listCate);
        return "admin/product-line/create";
    }

    @PostMapping("/create")
    public String handleCreateForm(@Valid @ModelAttribute("newProductLine") ProductLineDto newProductLine,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("productLineImg") MultipartFile file,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("error from product line create");
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newProductLine", bindingResult);
            redirectAttributes.addFlashAttribute("newProductLine", newProductLine);

            System.out.println(model.toString()); //here the model contains the two above
            return "redirect:/admin/product-line/create";

            //if redirect falls, we can directly pass the model that contains data do the function
            //return this.getProductLineCreate(model);
        }



        //decided to stop the validation error before it reach the service layer
        //purpose is to seperate the dependency between layers
        ProductLine productLine = this.productLineService.dtoConvert(newProductLine);

        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "product-lines/");

            productLine.setImage(fileName);

        }

        this.productLineService.saveProductLine(productLine);

//        System.out.println(newProductLine.getCategory() + " from controller");
//        Category category = this.categoryService.getCategoryById(productLine.getCategory().getId());
//        System.out.println(category.getProductLineList());


        return "redirect:/admin/product-line";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable("id") long id) {
        if (!model.containsAttribute("alterProdLine")) {
            ProductLineDto dto = this.productLineService.convert2Dto(this.productLineService.getByProdLineId(id));
            model.addAttribute("alterProdLine", dto);
        }

        List<Category> listCate = this.categoryService.getAllCategory();
        model.addAttribute("categoryList", listCate);
        //model.addAttribute("cateName", productLine.getCategory().getName());

        return "admin/product-line/update";
    }



    @PostMapping("/update/{id}")
    public String handleUpdateRequest(Model model,
                                @PathVariable("id") long id,
                                @Valid @ModelAttribute("alterProdLine") ProductLineDto dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @RequestParam("productLineImg") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from product line update");

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alterProdLine", bindingResult);
            redirectAttributes.addFlashAttribute("alterProdLine", dto);

            return "redirect:/admin/product-line/update/" + id;
        }


        ProductLine current = this.productLineService.getByProdLineId(id);
        ProductLine alterProdLine = this.productLineService.dtoConvert(dto);
        //ngu
        alterProdLine.setProductSeriesList(current.getProductSeriesList());


        if (file.isEmpty()) {
            System.out.println("no file uploaded");
            alterProdLine.setImage(current.getImage());
        } else {
            String fileName = this.simpleFileService.handleFileUpload(file, "product-lines/");
            alterProdLine.setImage(fileName);
            this.simpleFileService.handleDeleteFile(current.getImage(), "product-lines/");
        }

        ProductLine saved = this.productLineService.saveProductLine(alterProdLine);


        //dont use cascade merge
        //Category category = this.categoryService.getCategoryById(36);
        //loi nay la do cascade
        //System.out.println(category.getProductLineList());
        //System.out.println("from product: " + saved.getCategory().getProductLineList());


        return "redirect:/admin/product-line/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteProdLine(@PathVariable("id") long id) {
        this.productLineService.deleteById(id);
        return "redirect:/admin/product-line";
    }

    @GetMapping("/{id}")
    public String getProductLineDetails(Model model,
                                        @PathVariable("id") long id) {
        ProductLine productLine = this.productLineService.getByProdLineId(id);
        model.addAttribute("theProductLine", productLine);
        return "admin/product-line/details";
    }

}
