package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.dto.ColorDto;
import com.chikacow.pet_project.dto.FeatureDto;
import com.chikacow.pet_project.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    private final ProductLineService productLineService;
    private final ProductService productService;
    private final FeatureService featureService;
    private final SimpleUploadService simpleUploadService;

    private final ScheduleService scheduleService;

    public AdminProductController(ProductLineService productLineService, ProductService productService, FeatureService featureService, SimpleUploadService simpleUploadService, ScheduleService scheduleService) {
        this.productLineService = productLineService;
        this.productService = productService;
        this.featureService = featureService;
        this.simpleUploadService = simpleUploadService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public String getProductList(Model model) {
        List<Product> listProduct = this.productService.getAllProducts();
        model.addAttribute("productList", listProduct);


        return "admin/product/list";
    }

    @GetMapping("/create")
    public String getProductCreateForm(Model model) {
        this.scheduleService.pauseTask();

        Product blank = new Product();
        Product productWid = this.productService.saveProduct(blank);

        model.addAttribute("newProduct", productWid);
        model.addAttribute("productId", productWid.getId());


        FeatureDto feature = new FeatureDto();
        model.addAttribute("newFeature", feature);

        ColorDto color = new ColorDto();
        model.addAttribute("newColor", color);

        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);





        return "admin/product/create";
    }

    @GetMapping("/create/{id}")
    public String getProductonCreateprocessForm(Model model,
                                                @PathVariable("id") long productId) {

        this.scheduleService.pauseTask();
        Product creatingProduct = this.productService.getProductById(productId);

        model.addAttribute("newProduct", creatingProduct);

        model.addAttribute("productId", productId);


        FeatureDto feature = new FeatureDto();
        model.addAttribute("newFeature", feature);

        Color color = new Color();
        model.addAttribute("newColor", color);

        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);


        List<Feature> listFeature = this.featureService.getAllFeatureByProductId(productId);
        model.addAttribute("featureList", listFeature);
        //System.out.println(creatingProduct.getFeatures());

        return "admin/product/create";

    }

    @PostMapping("/create")
    public String handleCreateRequest(Model model,
                                      @ModelAttribute("newProduct") Product newProduct,
                                      @RequestParam("productImg")MultipartFile file) {


        Product product = newProduct;
        if (!file.isEmpty()) {
            String fileName = this.simpleUploadService.handleFileUpload(file, "products/");
            product.setMainImage(fileName);
        }

        System.out.println(product.isWannaCreate());

        product.setWannaCreate(true);

        System.out.println(product.isWannaCreate());

        List<Feature> list = this.featureService.getAllFeatureByProductId(product.getId());
        product.setFeatures(list);

        this.productService.saveProduct(product);

        this.scheduleService.resumeTask();


        return "redirect:/admin/product";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm() {
        return "";
    }

    @PostMapping("/update/{id}")
    public String handleUpdateRequest() {
        return "";
    }

    @GetMapping("/{id}")
    public String getProductDetails() {
        return "";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct() {
        return "";
    }
}
