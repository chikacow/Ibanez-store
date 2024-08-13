package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.service.ColorService;
import com.chikacow.pet_project.service.ProductService;
import com.chikacow.pet_project.service.SimpleFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/color")
public class AdminColorController {
    private final ColorService colorService;
    private final ProductService productService;
    private final SimpleFileService simpleUploadService;

    public AdminColorController(ColorService colorService, ProductService productService, SimpleFileService simpleUploadService) {
        this.colorService = colorService;
        this.productService = productService;
        this.simpleUploadService = simpleUploadService;
    }

    @PostMapping("/create/{id}")
    public String createNewColor(Model model,
                                 @ModelAttribute("newColor") Color color,
                                 @PathVariable("id") long productId,
                                 @RequestParam("colorImg") MultipartFile file) {

        if (!file.isEmpty()) {
            String fileName = this.simpleUploadService.handleFileUpload(file, "colors/");
            color.setImage(fileName);
        }

        Product product = this.productService.getProductById(productId);


        color.getProductList().add(product);


        Color saved = this.colorService.saveColor(color);


        product.getColors().add(saved);
        this.productService.saveProduct(product);


        System.out.println(saved);
        System.out.println(color.getId());
        System.out.println(product.getColors());
        System.out.println();


        return "redirect:/admin/product/create/" + productId;
    }
}
