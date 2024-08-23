package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.*;
import com.chikacow.pet_project.service.*;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("admin/artist")
public class AdminArtistController {

    private final ArtistService artistService;
    private final CategoryService categoryService;
    private final SignatureProductService signatureProductService;
    private final SimpleFileService simpleFileService;

    private final ProductSeriesService productSeriesService;

    public AdminArtistController(ArtistService artistService, CategoryService categoryService, SignatureProductService signatureProductService, SimpleFileService simpleFileService, ProductSeriesService productSeriesService) {
        this.artistService = artistService;
        this.categoryService = categoryService;

        this.signatureProductService = signatureProductService;
        this.simpleFileService = simpleFileService;
        this.productSeriesService = productSeriesService;
    }

    @GetMapping
    public String showAllArtist(Model model) {
        List<Artist> artistList = this.artistService.getAllArtist();
        model.addAttribute("artistList", artistList);


        return "admin/artist/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        Artist newArtist = this.artistService.saveArtist(new Artist());
        model.addAttribute("newArtist", newArtist);
        model.addAttribute("artistId", newArtist.getId());

        List<Category> list = this.categoryService.getAllCategory();
        model.addAttribute("cateList", list);

        model.addAttribute("blankProduct", new Product());

        return "admin/artist/create";
    }

    @GetMapping("/create/{id}")
    public String getCreateFormOnProcess(Model model,
                                         @PathVariable("id") long artistId) {
        Artist newArtist = this.artistService.getArtistById(artistId);

        if (!model.containsAttribute("newArtist")) {

            model.addAttribute("newArtist", newArtist);
        }
        model.addAttribute("artistId", newArtist.getId());

        List<Category> list = this.categoryService.getAllCategory();
        model.addAttribute("cateList", list);

        model.addAttribute("blankProduct", new Product());

        List<SignatureProduct> sigProdList = this.signatureProductService.getAllSignatureProductByArtistId(artistId);
        model.addAttribute("signatureProductList", sigProdList);

        return "admin/artist/create";
    }

    @PostMapping("/create")
    public String handleCreateArtist(Model model,
                                     @Valid @ModelAttribute("newArtist") Artist newArtist,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam("artistImg") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error from artist create");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newArtist", bindingResult);
            redirectAttributes.addFlashAttribute("newArtist", newArtist);


            return "redirect:/admin/artist/create/" + newArtist.getId();

        }

        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "artists/");
            newArtist.setImage(fileName);
        }

        System.out.println(newArtist);

        List<SignatureProduct> listSig = this.signatureProductService.getAllSignatureProductByArtistId(newArtist.getId());
        newArtist.setProductList(listSig);

        Artist saved = this.artistService.saveArtist(newArtist);

        System.out.println(saved);
        
        return "redirect:/admin/artist";

    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable("id") long artistId) {

        if (!model.containsAttribute("alterArtist")) {
            Artist alterArtist = this.artistService.getArtistById(artistId);
            model.addAttribute("alterArtist", alterArtist);
        }

        List<Category> cateList = this.categoryService.getAllCategory();
        model.addAttribute("cateList", cateList);

        List<SignatureProduct> signatureProductList = this.signatureProductService.getAllSignatureProductByArtistId(artistId);
        model.addAttribute("signatureProductList", signatureProductList);
        model.addAttribute("artistId", artistId);

        model.addAttribute("blankProduct", new Product());

        return "admin/artist/update";

    }

    @PostMapping("/update/{id}")
    public String handleUpdateRequest(Model model,
                                      @PathVariable("id") long artistId,
                                      @Valid @ModelAttribute("alterArtist") Artist alterArtist,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @RequestParam("artistImg") MultipartFile file) {


        if (bindingResult.hasErrors()) {
            System.out.println("Error from artist create");

            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alterArtist", bindingResult);
            redirectAttributes.addFlashAttribute("alterArtist", alterArtist);


            return "redirect:/admin/artist/update/" + artistId;

        }

        if (!file.isEmpty()) {
            String fileName = this.simpleFileService.handleFileUpload(file, "artists/");
            alterArtist.setImage(fileName);
        }
        List<SignatureProduct> signatureProductList = this.signatureProductService.getAllSignatureProductByArtistId(artistId);

        alterArtist.setProductList(signatureProductList);
        this.artistService.saveArtist(alterArtist);
        System.out.println(alterArtist);
        return "redirect:/admin/artist/update/" + artistId;
    }

    @GetMapping("{id}")
    public String getDetails(Model model,
                             @PathVariable("id") long artistId) {
        Artist artist = this.artistService.getArtistById(artistId);
        model.addAttribute("theArtist", artist);

        return "admin/artist/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable("id") long artistId) {
        this.artistService.deleteArtistById(artistId);
        return "redirect:/admin/artist";
    }

}
