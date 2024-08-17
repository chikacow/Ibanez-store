package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.*;
import com.chikacow.pet_project.dto.FeatureDto;
import com.chikacow.pet_project.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/signature-product")
public class AdminSignatureProduct {
    private final SignatureProductService signatureProductService;
    private final ProductService productService;
    private final FeatureService featureService;
    private final ColorService colorService;
    private final SimpleFileService simpleFileService;
    private final ScheduleService scheduleService;
    private final ProductLineService productLineService;
    private final ArtistService artistService;

    public AdminSignatureProduct(SignatureProductService signatureProductService, ProductService productService, FeatureService featureService, ColorService colorService, SimpleFileService simpleFileService, ScheduleService scheduleService, ProductLineService productLineService, ArtistService artistService) {
        this.signatureProductService = signatureProductService;
        this.productService = productService;
        this.featureService = featureService;
        this.colorService = colorService;
        this.simpleFileService = simpleFileService;
        this.scheduleService = scheduleService;
        this.productLineService = productLineService;
        this.artistService = artistService;
    }

    @GetMapping
    public String getListSignatureProduct() {
        System.out.println(LocalDateTime.now());
        return "sample";
    }


    @GetMapping("/create")
    public String getSignatureProductCreateForm(Model model,
                                                @RequestParam(value = "artistId", required = false) long artistId) {
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

        model.addAttribute("artistId", artistId);





        return "admin/signature-product/create";
    }

    @GetMapping("/create/{id}")
    public String getSignatureProductOnCreateProcessForm(Model model,
                                                @PathVariable("id") long productId) {

        this.scheduleService.pauseTask();
        if (!model.containsAttribute("newProduct")) {
            Product creatingProduct = this.productService.getProductById(productId);

            model.addAttribute("newProduct", creatingProduct);

        }

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

        return "admin/signature-product/create";

    }

    @PostMapping("/create")
    public String handleCreateRequest(Model model,
                                      @Valid @ModelAttribute("newProduct") Product newProduct,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                      @RequestParam("productImg") MultipartFile file,
                                      @RequestParam(value = "artistId", required = false) long artistId) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from signature product create");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newProduct", bindingResult);
            redirectAttributes.addFlashAttribute("newProduct", newProduct);


            return "redirect:/admin/signature-product/create/" + newProduct.getId() + "?artistId=" + artistId;

        }

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

        Product saved = this.productService.saveProduct(product);

        SignatureProduct newSignatureProduct = new SignatureProduct();
        newSignatureProduct.setProduct(saved);

        Artist artist = this.artistService.getArtistById(artistId);
        newSignatureProduct.setArtist(artist);
        //signatureProduct.setDateAdded(LocalDateTime.now().toString());

        this.signatureProductService.saveSignatureProduct(newSignatureProduct);

        this.scheduleService.resumeTask();


        return "redirect:/admin/artist/create/" + artistId;
    }



    @PostMapping("/assign-to-artist")
    public String assignProductToArtist(@ModelAttribute("blankProduct") Product blankProduct,
                                        @RequestParam(value = "artistId", required = false) long artistId,
                                        @RequestParam(value = "fromArtistUpdate", required = false, defaultValue = "false") boolean fromArtistUpdate) {
        Product product = this.productService.getProductByName(blankProduct.getName());

        Artist artist = this.artistService.getArtistById(artistId);
        SignatureProduct signatureProduct = new SignatureProduct();
        signatureProduct.setProduct(product);

        // draft
        try {


            if (signatureProduct.getArtist() != null && signatureProduct.getArtist().equals(artist)) {
                System.out.println("thang artist nay co roi");
            } else {
                signatureProduct.setArtist(artist);
            }

            SignatureProduct saved = this.signatureProductService.saveSignatureProduct(signatureProduct);

            if (artist.getProductList().contains(saved)) {
                System.out.println("co r ma wtf");
            } else {
                artist.getProductList().add(saved);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.artistService.saveArtist(artist);

        if (fromArtistUpdate) {
            return "redirect:/admin/artist/update/" + artistId;
        }

        return "redirect:/admin/artist/create/" + artistId;
    }

    @GetMapping("/delete/{id}")
    public String deleteSignatureProduct(@PathVariable("id") long sigProdId) {
        this.signatureProductService.deleteById(sigProdId);
        return "redirect:/admin/artist";
    }

}
