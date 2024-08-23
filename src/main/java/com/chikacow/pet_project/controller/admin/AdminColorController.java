package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Feature;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.service.ColorService;
import com.chikacow.pet_project.service.ProductService;
import com.chikacow.pet_project.service.SimpleFileService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/color")
public class AdminColorController {
    private final ColorService colorService;
    private final ProductService productService;
    private final SimpleFileService simpleFileService;
    private final RedirectAttributes redirectAttributes;

    public AdminColorController(ColorService colorService, ProductService productService, SimpleFileService simpleFileService, RedirectAttributes redirectAttributes) {
        this.colorService = colorService;
        this.productService = productService;
        this.simpleFileService = simpleFileService;
        this.redirectAttributes = redirectAttributes;
    }

    @GetMapping("/create/{id}")
    public String getNormalCreateForm(Model model,
                                      @PathVariable("id") long productId,
                                      @RequestParam(value = "onUpdate", defaultValue = "false", required = false) boolean onUpdate,
                                      @RequestParam(value = "onArtistUpdate", defaultValue = "false", required = false) boolean onArtistUpdate) {

        if (!model.containsAttribute("newColor")) {
            Color color = new Color();
            model.addAttribute("newColor", color);

        }

        //model.addAttribute("newColor", new Color());
        model.addAttribute("productId", productId);

        model.addAttribute("onUpdate", onUpdate);
        model.addAttribute("onArtistUpdate", onArtistUpdate);
        return "admin/product/color/create";
    }

    @PostMapping("/create/{id}")
    public String createNewColor(Model model,
                                 @Valid @ModelAttribute("newColor") Color color,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @PathVariable("id") long productId,
                                 @RequestParam("colorImg") MultipartFile file,
                                 @RequestParam(value = "onUpdate", required = false, defaultValue = "false") boolean onUpdate,
                                 @RequestParam(value = "selfCreate", required = false, defaultValue = "false") boolean selfCreate,
                                 @RequestParam(value = "artistId", required = false) Long artistId,
                                 @RequestParam(value = "onSigProdCreate", defaultValue = "false", required = false) boolean onSigProdCreate,
                                 @RequestParam(value = "onArtistUpdate", defaultValue = "false", required = false) boolean onArtistUpdate) {


        if (bindingResult.hasErrors()) {
            System.out.println("Error from color create");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newColor", bindingResult);
            redirectAttributes.addFlashAttribute("newColor", color);

            if (selfCreate) {
                return "redirect:/admin/color/create/" + productId + "?onUpdate=" + onUpdate;
            }

            if (onUpdate==false) {
                if (onArtistUpdate) {
                    return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate + "&onArtistUpdate=" + onArtistUpdate;
                }
                return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate; //or testId
            } else {
                return "redirect:/admin/product/update/" + productId;
            }

        }

        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "colors/");
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

        if (onSigProdCreate) {
            if (onArtistUpdate) {
                return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate + "&onArtistUpdate=" + onArtistUpdate;
            }
            return "redirect:/admin/product/create/" + productId + "?artistId=" + artistId + "&onSigProdCreate=" + onSigProdCreate; //or productId
        }

        if (onUpdate) {
            return "redirect:/admin/product/update/" + productId;
        } else {
            return "redirect:/admin/product/create/" + productId;
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateColorForm(Model model,
                                     @PathVariable("id") long colorId,
                                     @RequestParam(value = "productId", required = false) Long productId,
                                     @RequestParam(value = "onCreate", defaultValue = "false", required = false) boolean onCreate) {
        if (!model.containsAttribute("alterColor")) {
            Color color = this.colorService.getColorById(colorId);

            model.addAttribute("alterColor", color);
        }
        model.addAttribute("colorId", colorId);

        model.addAttribute("productId", productId);


        model.addAttribute("onCreate", onCreate);



        return "admin/product/color/update";
    }

    @PostMapping("/update/{id}")
    public String handleColorUpdate(Model model,
                                    @Valid @ModelAttribute("alterColor") Color alterColor,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable("id") Long colorId,
                                    @RequestParam("colorImg") MultipartFile file,
                                    @RequestParam(value = "productId", required = false) Long productId,
                                    @RequestParam(value = "onCreate", defaultValue = "false", required = false) boolean onCreate) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from color update");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alterColor", bindingResult);
            redirectAttributes.addFlashAttribute("alterColor", alterColor);

            return "redirect:/admin/color/update/" + colorId + "?productId=" + productId;

        }

        Color current = this.colorService.getColorById(colorId);

        alterColor.setProductList(current.getProductList());

        if (file.isEmpty()) {
            System.out.println("file isnt even found");
            alterColor.setImage(current.getImage());
        } else {
            String fileName = this.simpleFileService.handleFileUpload(file, "colors/");
            alterColor.setImage(fileName);
            this.simpleFileService.handleDeleteFile(current.getImage(), "colors/");
        }


        System.out.println(alterColor);

        this.colorService.saveColor(alterColor);

        System.out.println(alterColor);

        if (onCreate) {
            return "redirect:/admin/product/create/" + productId;
        } else {
            return "redirect:/admin/product/update/" + productId;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteColorFromProduct(Model model,
                                         @PathVariable("id") long colorId,
                                         @RequestParam(value = "productId", required = true) long productId) {
        Product product = this.productService.getProductById(productId);
        Color color = this.colorService.getColorById(colorId);
        product.getColors().remove(color);
        this.productService.saveProduct(product);
        return "redirect:/admin/product/update/" + productId;
    }

    @GetMapping("/{id}")
    public String getDetails(Model model,
                             @PathVariable("id") long colorId,
                             @RequestParam(value = "productId", required = false) long productId) {
        Color color = this.colorService.getColorById(colorId);

        model.addAttribute("theColor", color);

        Product product = this.productService.getProductById(productId);

        model.addAttribute("theProduct", product);

        return "admin/product/color/details";
    }



}
