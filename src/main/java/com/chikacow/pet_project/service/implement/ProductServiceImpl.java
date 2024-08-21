package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.Product;
import com.chikacow.pet_project.domain.SignatureProduct;
import com.chikacow.pet_project.dto.ProductDto;
import com.chikacow.pet_project.repository.ProductRepository;
import com.chikacow.pet_project.service.ProductService;
import com.chikacow.pet_project.service.SignatureProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final SignatureProductService signatureProductService;

    public ProductServiceImpl(ProductRepository productRepository, SignatureProductService signatureProductService) {
        this.productRepository = productRepository;
        this.signatureProductService = signatureProductService;
    }

    @Override
    public Product saveProduct(Product product) {
        Optional<Product> container = this.productRepository.findById(product.getId());
        if (container.isEmpty()) {
            product.setName("name");
            product.setDescription("des");
            Product saved = this.productRepository.save(product);
            saved.setName("");
            saved.setDescription("");
            return saved;
        } else {
            return this.productRepository.save(product);
        }


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
    public void deleteProductById(long id) {
        SignatureProduct sig = this.signatureProductService.getSignatureProductByProductId(id);

        if (sig != null) {
            this.signatureProductService.deleteById(sig.getId());

        }

        this.productRepository.deleteById(id);

    }

    @Override
    public Product getProductByName(String name) {
        Optional<Product> container = this.productRepository.findByName(name);
        if (container.isEmpty()) {
            return null;
        }
        return container.get();
    }


}
