package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.domain.ProductSeries;
import com.chikacow.pet_project.dto.ProductLineDto;
import com.chikacow.pet_project.service.ProductLineService;
import com.chikacow.pet_project.service.ProductSeriesService;
import com.chikacow.pet_project.service.SimpleFileService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/product-series")
public class AdminProdSeriesController {
    private final ProductSeriesService productSeriesService;
    private final ProductLineService productLineService;
    private final SimpleFileService simpleFileService;

    public AdminProdSeriesController(ProductSeriesService productSeriesService, ProductLineService productLineService, SimpleFileService simpleFileService) {
        this.productSeriesService = productSeriesService;
        this.productLineService = productLineService;
        this.simpleFileService = simpleFileService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<ProductSeries> list = this.productSeriesService.getAllProductSeries();
        model.addAttribute("prodSeriesList", list);

        return "admin/product-series/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {

        if (!model.containsAttribute("newProductSeries")) {

            model.addAttribute("newProductSeries", new ProductSeries());
        }

        //model.addAttribute("newProductSeries", new ProductSeries());

        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);

        return "admin/product-series/create";

    }

    @PostMapping("/create")
    public String handleCreateForm(@Valid @ModelAttribute("newProductSeries") ProductSeries newProductSeries,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("productSeriesImg") MultipartFile file,
                                   Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            System.out.println("error from product series create");
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newProductSeries", bindingResult);
            redirectAttributes.addFlashAttribute("newProductSeries", newProductSeries);

            System.out.println(model.toString()); //here the model contains the two above
            return "redirect:/admin/product-series/create";

            //if redirect falls, we can directly pass the model that contains data do the function
            //return this.getProductLineCreate(model);
        }



        //decided to stop the validation error before it reach the service layer
        //purpose is to seperate the dependency between layers


        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "product-series/");

            newProductSeries.setImage(fileName);

        }

        //xu ly chuoi


        String modifiedName = this.productSeriesService.getNameModified(newProductSeries.getName(),newProductSeries.getProductLine().getName());
        newProductSeries.setName(modifiedName);
        this.productSeriesService.saveProductSeries(newProductSeries);

        System.out.println(newProductSeries);



//        System.out.println(newProductLine.getCategory() + " from controller");
//        Category category = this.categoryService.getCategoryById(productLine.getCategory().getId());
//        System.out.println(category.getProductLineList());


        return "redirect:/admin/product-series";
    }


    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable("id") long id) {
        if (!model.containsAttribute("alterProdSeries")) {
            ProductSeries productSeries = this.productSeriesService.getProdSeriesById(id);
            model.addAttribute("alterProdSeries", productSeries);
        }

        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);


        return "admin/product-series/update";
    }

    @PostMapping("/update/{id}")
    public String handleUpdateRequest(Model model,
                                      @PathVariable("id") long id,
                                      @Valid @ModelAttribute("alterProdSeries") ProductSeries alterProdSeries,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @RequestParam("productSeriesImg") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from product series update");

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alterProdSeries", bindingResult);
            redirectAttributes.addFlashAttribute("alterProdSeries", alterProdSeries);

            return "redirect:/admin/product-series/update/" + id;
        }


        ProductSeries current = this.productSeriesService.getProdSeriesById(id);


        if (file.isEmpty()) {
            System.out.println("no file uploaded");
            alterProdSeries.setImage(current.getImage());
        } else {
            String fileName = this.simpleFileService.handleFileUpload(file, "product-series/");
            alterProdSeries.setImage(fileName);
            this.simpleFileService.handleDeleteFile(current.getImage(), "product-series/");
        }

        String modifiedName = this.productSeriesService.getNameModified(alterProdSeries.getName(),alterProdSeries.getProductLine().getName());
        alterProdSeries.setName(modifiedName);
        this.productSeriesService.saveProductSeries(alterProdSeries);



        //return "redirect:/admin/product-series/update/{id}";
        return "redirect:/admin/product-series";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductSeries(@PathVariable("id") long id) {

        this.productSeriesService.deleteProductSeriesById(id);

        return "redirect:/admin/product-series";

    }

    @GetMapping("/{id}")
    public String getDetails(Model model,
                             @PathVariable("id") long id) {
        ProductSeries productSeries = this.productSeriesService.getProdSeriesById(id);
        model.addAttribute("theProductSeries", productSeries);
        return "admin/product-series/details";
    }


}
