package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.dto.ProductDto;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product saveProduct(Product product);
    public List<Product> getAllProducts();

    public Product getProductById(long id);
    public void deleteAllProducts();

    public Product dtoConvert(ProductDto dto);

    public ProductDto convert2Dto(Product product);

    public void deleteProductById(long id);

    public Product getProductByName(String name);

}
