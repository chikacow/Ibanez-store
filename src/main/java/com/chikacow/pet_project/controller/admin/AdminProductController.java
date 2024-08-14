package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.domain.SignatureProduct;
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
    private final SimpleFileService simpleFileService;
    private final ScheduleService scheduleService;
    private final ColorService colorService;

    private final SignatureProductService signatureProductService;

    public AdminProductController(ProductLineService productLineService, ProductService productService, FeatureService featureService, SimpleFileService simpleFileService, ScheduleService scheduleService, ColorService colorService, SignatureProductService signatureProductService) {
        this.productLineService = productLineService;
        this.productService = productService;
        this.featureService = featureService;
        this.simpleFileService = simpleFileService;
        this.scheduleService = scheduleService;
        this.colorService = colorService;
        this.signatureProductService = signatureProductService;
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

        Color color = new Color();
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

        //List<Color> listColor = creatingProduct.getColors();
        List<Color> listColor = this.colorService.getAllColorByProductId(productId);
        model.addAttribute("colorList", listColor);

        return "admin/product/create";

    }

    @PostMapping("/create")
    public String handleCreateRequest(Model model,
                                      @ModelAttribute("newProduct") Product newProduct,
                                      @RequestParam("productImg")MultipartFile file) {


        Product product = newProduct;
        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "products/");
            product.setMainImage(fileName);
        }

        System.out.println(product.isOnDemand());

        product.setOnDemand(true);

        System.out.println(product.isOnDemand());

        List<Feature> list = this.featureService.getAllFeatureByProductId(product.getId());
        product.setFeatures(list);

        this.productService.saveProduct(product);

        this.scheduleService.resumeTask();


        return "redirect:/admin/product";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable("id") long id,
                                @RequestParam(value = "fromSignature", required = false, defaultValue = "false") boolean fromSignature) {

        Product alterProduct = this.productService.getProductById(id);
        model.addAttribute("alterProduct", alterProduct);

        model.addAttribute("productId", id);

        List<ProductLine> list = this.productLineService.getAllProdLine();
        model.addAttribute("prodLineList", list);

        FeatureDto feature = new FeatureDto();
        model.addAttribute("newFeature", feature);

        Color color = new Color();
        model.addAttribute("newColor", color);


        model.addAttribute("fromSignature", fromSignature);




        return "admin/product/update";
    }

    @PostMapping("/update/{id}")
    public String handleUpdateRequest(Model model,
                                      @PathVariable("id") long productId,
                                      @ModelAttribute("alterProduct") Product alterProduct,
                                      @RequestParam("productImg") MultipartFile file,
                                      @RequestParam(value = "fromSignature", required = false, defaultValue = "false") boolean fromSignature) {

        Product current = this.productService.getProductById(productId);
        System.out.println(alterProduct);


        alterProduct.setColors(current.getColors());
        alterProduct.setOnDemand(current.isOnDemand());
        alterProduct.setFeatures(current.getFeatures());

        if (file.isEmpty()) {
            alterProduct.setMainImage(current.getMainImage());
            System.out.println("no file uploaded");
        } else {
            String fileName = this.simpleFileService.handleFileUpload(file, "products/");
            alterProduct.setMainImage(fileName);
            System.out.println("old image: " + current.getMainImage());
            String deleteFile = this.simpleFileService.handleDeleteFile(current.getMainImage(), "products/");
            System.out.println(deleteFile);

        }


        this.productService.saveProduct(alterProduct);



        if (fromSignature) {
            SignatureProduct signatureProduct = this.signatureProductService.getSignatureProductByProductId(productId);
            long artistId = signatureProduct.getArtist().getId();
            return "redirect:/admin/artist/update/" + artistId;
        }


        return "redirect:/admin/product/update/" + productId;
    }

    @GetMapping("/{id}")
    public String getProductDetails() {
        return "";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId) {
        this.productService.deleteProductById(productId);

        return "redirect:/admin/product";
    }
}
