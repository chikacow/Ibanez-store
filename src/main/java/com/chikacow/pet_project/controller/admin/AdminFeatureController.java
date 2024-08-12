package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.dto.FeatureDto;
import com.chikacow.pet_project.service.FeatureService;
import com.chikacow.pet_project.service.ProductService;
import com.chikacow.pet_project.service.SimpleUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/feature")
public class AdminFeatureController {

    private final FeatureService featureService;
    private final ProductService productService;
//    private final RedirectAttributes redirectAttributes;

    private final SimpleUploadService simpleUploadService;

    @Autowired
    public AdminFeatureController(FeatureService featureService, ProductService productService, SimpleUploadService simpleUploadService) {
        this.featureService = featureService;
        this.productService = productService;

        this.simpleUploadService = simpleUploadService;
    }

    @PostMapping("/create/{id}")
    public String createNewFeature(@ModelAttribute("newFeature") FeatureDto featureDto,
                                   @PathVariable("id") long productId,
                                   @RequestParam("testId") long testId,
                                   @RequestParam("featureImage") MultipartFile file) {

        Feature feature = this.featureService.convert2Entity(featureDto);
        feature.setProduct(this.productService.getProductById(testId)); //or productid
        //2 ways to solve this, pathvariable or requestparam. all input with result in requestparam

        if (!file.isEmpty()) {
            String fileName = this.simpleUploadService.handleFileUpload(file, "features/");

            feature.setImage(fileName);

        }


        this.featureService.saveFeature(feature);

        //long productId = featureDto.getProductId();


        return "redirect:/admin/product/create/" + testId; //or productId

    }
}
