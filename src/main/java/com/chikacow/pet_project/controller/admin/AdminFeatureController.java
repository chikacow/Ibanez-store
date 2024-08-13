package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.dto.FeatureDto;
import com.chikacow.pet_project.service.FeatureService;
import com.chikacow.pet_project.service.ProductService;
import com.chikacow.pet_project.service.SimpleFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/feature")
public class AdminFeatureController {

    private final FeatureService featureService;
    private final ProductService productService;
//    private final RedirectAttributes redirectAttributes;

    private final SimpleFileService simpleFileService;

    @Autowired
    public AdminFeatureController(FeatureService featureService, ProductService productService, SimpleFileService simpleFileService) {
        this.featureService = featureService;
        this.productService = productService;

        this.simpleFileService = simpleFileService;
    }

    @PostMapping("/create/{id}")
    public String createNewFeature(@ModelAttribute("newFeature") FeatureDto featureDto,
                                   @PathVariable("id") long productId,
                                   @RequestParam("testId") long testId,
                                   @RequestParam("featureImage") MultipartFile file,
                                   @RequestParam(value = "onUpdate", required = false, defaultValue = "false") boolean onUpdate) {

        Feature feature = this.featureService.convert2Entity(featureDto);
        feature.setProduct(this.productService.getProductById(testId)); //or productid
        //2 ways to solve this, pathvariable or requestparam. all input with result in requestparam

        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "features/");

            feature.setImage(fileName);

        }


        this.featureService.saveFeature(feature);

        //long productId = featureDto.getProductId();

        if (onUpdate == false) {

            return "redirect:/admin/product/create/" + testId; //or productId
        } else {
            return "redirect:/admin/product/update/" + testId;
        }

    }

    @GetMapping("/update/{id}")
    public String getCreateFeatureForm(Model model,
                                       @PathVariable("id") long id) {
        Feature onUpdate = this.featureService.getFeatureById(id);
        model.addAttribute("alterFeature", onUpdate);

        long productId = onUpdate.getProduct().getId();
        model.addAttribute("productId", productId);

        model.addAttribute("featureId", id);

        return "admin/product/feature/update";
    }

    @PostMapping("/update/{id}")
    public String handleFeatureUpdate(Model model,
                                      @ModelAttribute("alterFeature") Feature alterFeature,
                                      @PathVariable("id") long featureId,
                                      @RequestParam("featureImage") MultipartFile file) {

        Feature current = this.featureService.getFeatureById(featureId);
        System.out.println(alterFeature);

        long productId = alterFeature.getProduct().getId();
        model.addAttribute("productId", productId);

        if (file.isEmpty()) {
            System.out.println("no file uploaded");
            alterFeature.setImage(current.getImage());
        } else {
            String fileName = this.simpleFileService.handleFileUpload(file, "features/");
            alterFeature.setImage(fileName);
            this.simpleFileService.handleDeleteFile(current.getImage(), "features/");
        }

        this.featureService.saveFeature(alterFeature);



        return "redirect:/admin/product/update/" + productId;

    }

    @GetMapping("/delete/{id}")
    public String handleDeleteFeature(Model model,
                               @PathVariable("id") long featureId) {
        Feature feature = this.featureService.getFeatureById(featureId);
        long productId = feature.getProduct().getId();

        this.featureService.deleteFeatureById(featureId);

        return "redirect:/admin/product/update/" + productId;
    }
}
