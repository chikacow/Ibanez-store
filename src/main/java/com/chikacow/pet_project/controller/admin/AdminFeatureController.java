package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.dto.FeatureDto;
import com.chikacow.pet_project.service.FeatureService;
import com.chikacow.pet_project.service.ProductService;
import com.chikacow.pet_project.service.SimpleFileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/create/{id}")
    public String getNormalCreateForm(Model model,
                                      @PathVariable("id") long productId,
                                      @RequestParam(value = "onUpdate", defaultValue = "false", required = false) boolean onUpdate,
                                      @RequestParam(value = "onArtistUpdate", defaultValue = "false", required = false) boolean onArtistUpdate) {

        if (!model.containsAttribute("newFeature")) {
            FeatureDto feature = new FeatureDto();
            model.addAttribute("newFeature", feature);
        }

        //model.addAttribute("newFeature", new Feature());
        model.addAttribute("productId", productId);

        model.addAttribute("onUpdate", onUpdate);
        model.addAttribute("onArtistUpdate", onArtistUpdate);

        return "admin/product/feature/create";
    }

    @PostMapping("/create/{id}")
    public String createNewFeature(@Valid @ModelAttribute("newFeature") FeatureDto featureDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @PathVariable("id") long productId,
                                   @RequestParam(value = "testId", required = false) Long testId,
                                   @RequestParam("featureImage") MultipartFile file,
                                   @RequestParam(value = "onUpdate", required = false, defaultValue = "false") boolean onUpdate,
                                   @RequestParam(value = "selfCreate", required = false, defaultValue = "false") boolean selfCreate,
                                   @RequestParam(value = "fromSignature", required = false, defaultValue = "false") boolean fromSignature,
                                   @RequestParam(value = "artistId", required = false) Long artistId,
                                   @RequestParam(value = "onSigProdCreate", defaultValue = "false", required = false) boolean onSigProdCreate,
                                   @RequestParam(value = "onArtistUpdate", defaultValue = "false", required = false) boolean onArtistUpdate) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from feature create");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newFeature", bindingResult);
            redirectAttributes.addFlashAttribute("newFeature", featureDto);

            if (selfCreate) {
                System.out.println("1");
                return "redirect:/admin/feature/create/" + productId + "?onUpdate=" + onUpdate;

            }

            if (onUpdate == false) {
                System.out.println("2");
                if (onArtistUpdate) {
                    return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate + "&onArtistUpdate=" + onArtistUpdate;
                }
                return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate; //or testId
            } else {
                System.out.println("3");
                return "redirect:/admin/product/update/" + productId;
            }

        }

        Feature feature = this.featureService.convert2Entity(featureDto);
        feature.setProduct(this.productService.getProductById(productId)); //or productid
        //2 ways to solve this, pathvariable or requestparam. all input with result in requestparam

        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "features/");

            feature.setImage(fileName);

        }


        this.featureService.saveFeature(feature);

        //long productId = featureDto.getProductId();

        if (onSigProdCreate) {
            if (onArtistUpdate) {
                return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate + "&onArtistUpdate=" + onArtistUpdate;
            }
            return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate; //or productId
        }
        if (onUpdate == false) {

            return "redirect:/admin/product/create/" + productId; //or productId
        } else {
            return "redirect:/admin/product/update/" + productId;
        }

    }

    @GetMapping("/update/{id}")
    public String getCreateFeatureForm(Model model,
                                       @PathVariable("id") long id,
                                       @RequestParam(value = "onCreate", defaultValue = "false",required = false) boolean onCreate) {

        Feature onUpdate = this.featureService.getFeatureById(id);
        if (!model.containsAttribute("alterFeature")) {

            model.addAttribute("alterFeature", onUpdate);
        }

        long productId = onUpdate.getProduct().getId();
        model.addAttribute("productId", productId);

        model.addAttribute("featureId", id);

        model.addAttribute("onCreate", onCreate);

        return "admin/product/feature/update";
    }

    @PostMapping("/update/{id}")
    public String handleFeatureUpdate(Model model,
                                      @Valid @ModelAttribute("alterFeature") Feature alterFeature,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @PathVariable("id") long featureId,
                                      @RequestParam("featureImage") MultipartFile file,
                                      @RequestParam(value = "onCreate", defaultValue = "false", required = false) boolean onCreate) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from feature update");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alterFeature", bindingResult);
            redirectAttributes.addFlashAttribute("alterFeature", alterFeature);

            return "redirect:/admin/feature/update/" + featureId;

        }
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


        System.out.println(onCreate);

        if (onCreate == true) {
            return "redirect:/admin/product/create/" + productId;
        } else {
            return "redirect:/admin/product/update/" + productId;

        }

    }

    @GetMapping("/delete/{id}")
    public String handleDeleteFeature(Model model,
                               @PathVariable("id") long featureId) {
        Feature feature = this.featureService.getFeatureById(featureId);
        long productId = feature.getProduct().getId();

        this.featureService.deleteFeatureById(featureId);

        return "redirect:/admin/product/update/" + productId;
    }

    @GetMapping("/{id}")
    public String getDetails(Model model,
                             @PathVariable("id") Long id) {
        Feature feature = this.featureService.getFeatureById(id);

        model.addAttribute("theFeature", feature);

        return "admin/product/feature/details";
    }


}
