package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.dto.ProductDto;
import com.chikacow.pet_project.repository.ProductRepository;
import com.chikacow.pet_project.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return this.productRepository.save(product);

    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> container = this.productRepository.findById(id);
        if (container.isEmpty()) {
            return null;
        }
        return container.get();
    }

    @Override
    public void deleteAllProducts() {
        this.productRepository.deleteAll();
    }

    @Override
    public Product dtoConvert(ProductDto dto) {
        return null;
    }

    @Override
    public ProductDto convert2Dto(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);

    }


}
