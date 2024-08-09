package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.dto.ProductDto;
import com.chikacow.pet_project.repository.ProductRepository;
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
    public void saveProduct(Product product) {
        this.productRepository.save(product);

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
