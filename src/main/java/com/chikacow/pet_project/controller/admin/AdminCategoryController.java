package com.chikacow.pet_project.controller.admin;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.repository.CategoryRepository;
import com.chikacow.pet_project.service.CategoryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //if there is nothing, then write completely nothing
    @GetMapping
    public String getCategoryList(Model model) {
        List<Category> list = this.categoryService.getAllCategory();
        model.addAttribute("cateList", list);

        return "admin/category/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        if (!model.containsAttribute("cate")) {
            model.addAttribute("newCategory", new Category());
        } else {
            model.addAttribute("newCategory", model.getAttribute("cate"));
            System.out.println("yes error");
        }

        return "admin/category/create";
    }

    @PostMapping("/create")
    public String postCreateForm(@Valid @ModelAttribute("newCategory") Category cate,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) throws ConstraintViolationException {
        //cate.setDescription(cate.getDescription() + "responsed");

        //o buoc nay chua nem ngoai le gi ca ma chi don thuan la check xem du lieu nhap vao co valid k
        if (bindingResult.hasErrors()) {
            System.out.println("oh no");
            //FieldError test = new FieldError("cate", "name", "test field error" );
            //bindingResult.addError(test);

            //redirect thi se k nhan dc binding result ben view tru khi lam nnay  /////////////////////////the newCategory is the object name from view
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newCategory", bindingResult);
            redirectAttributes.addFlashAttribute("cate", cate);
            return "redirect:/admin/category/create";

            //
            //return "admin/category/create";
        }

        //Day chinh la buoc nem ngoai le, neu k dc xu ly se dan toi loi chuong trinh
        //Co the try catch o day thi se k can trong global handle nua nhung k nen vi se kho bao tri
//        try {
            //thuc ra cx k can @valid vi save thuoc kieu persist da tu goi @Valid r
            this.categoryService.saveCategory(cate);

//        } catch (ConstraintViolationException e) {
//            System.out.println("oh no from posy");
//        }

        //this wont be call when the ex
        System.out.println("continue");
        //o day co 2 truong hop
        //th1 la ngoai le da duoc xu ly, khi ay bindingresult se het cac error va tro thanh null
        //th2 la khi ngoai le chua duoc xu ly, bindingresult van contain error. neu khi xu ly k tra ve
        //view nao thi spring se by default tra ve 1 view trong k co gi ca, all white
        if (bindingResult.hasErrors()) {
            System.out.println("oh no 2");
            //return "admin/category/create";
        }

        model.addAttribute("newCategory", cate);
        //return "redirect:/admin/category/create";
        return "redirect:/admin/category";

    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,
                                @PathVariable(name = "id") long id) {

        if (!model.containsAttribute("alterCategory")) {

            Category alterCategory = this.categoryService.getCategoryById(id);
            model.addAttribute("alterCategory", alterCategory);
        }
        model.addAttribute("theId",id);

        return "admin/category/update";


    }

    @PostMapping("/update/{id}")
    public String postUpdateForm(Model model,
                                @Valid @ModelAttribute("alterCategory") Category alterCategory,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @PathVariable(name = "id") long id) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error from category update");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alterCategory", bindingResult);
            redirectAttributes.addFlashAttribute("alterCategory", alterCategory);

            return "redirect:/admin/category/update/" + id;
        }
        //alterCategory.setDescription(alterCategory.getDescription() + "updated");


        System.out.println(alterCategory);
        //2 cach de giai quyet van de
        //1 la tao current, assign value tu alter sang current va save current
        //2 la van save alter nhung phai hung tat ca du lieu tu view, tuc la can input hidden rat nhieu
        model.addAttribute("alterCategory", alterCategory);


        this.categoryService.saveCategory(alterCategory);

        //return "admin/category/update";
        return "redirect:/admin/category/" + id;

    }



    @GetMapping("/delete/{id}")
    public String postDeleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    @GetMapping("{id}")
    public String getCategoryDetails(Model model,
                                     @PathVariable("id") long id) {

        Category category = this.categoryService.getCategoryById(id);
        model.addAttribute("theCategory", category);
        return "admin/category/details";
    }
}
