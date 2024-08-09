package com.chikacow.pet_project.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @GetMapping
    public String getProductList() {
        return "";
    }

    @GetMapping
    public String getProductCreateForm() {
        return "";
    }

    @PostMapping
    public String handleCreateRequest() {
        return "";
    }

    @GetMapping
    public String getUpdateForm() {
        return "";
    }

    @PostMapping
    public String handleUpdateRequest() {
        return "";
    }

    @GetMapping
    public String getProductDetails() {
        return "";
    }

    @GetMapping
    public String deleteProduct() {
        return "";
    }
}
