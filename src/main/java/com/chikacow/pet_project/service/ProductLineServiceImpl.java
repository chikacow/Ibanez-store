package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.dto.ProductLineDto;
import com.chikacow.pet_project.repository.ProductLineRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductLineServiceImpl implements ProductLineService {
    private final ProductLineRepository productLineRepository;
    private final CategoryService categoryService;

    private final EntityManager entityManager;

    public ProductLineServiceImpl(ProductLineRepository productLineRepository, CategoryService categoryService, EntityManager entityManager) {
        this.productLineRepository = productLineRepository;
        this.categoryService = categoryService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public ProductLine saveProductLine(ProductLine productLine) {

        ProductLine saved = this.productLineRepository.save(productLine);
        entityManager.flush();

        return saved;

    }

    @Override
    public ProductLine getByProdLineId(long id) {
        ProductLine productLine = this.productLineRepository.findById(id);
        return productLine;
    }

    @Override
    public List<ProductLine> getAllProdLine() {
        return this.productLineRepository.findAll();


    }

    @Override
    public void deleteById(long id) {
        this.productLineRepository.deleteById(id);

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public ProductLine dtoConvert(ProductLineDto productLineDto) {
        ProductLine productLine = new ProductLine();

        productLine.setId(productLineDto.getId());
        productLine.setName(productLineDto.getName());
        productLine.setDescription(productLineDto.getDescription());
        productLine.setCategory(this.categoryService.getCategoryById(productLineDto.getCategory()));
        //System.out.println(productLine.getCategory() + "from dtoconv");

        return productLine;
    }

    @Override
    public ProductLineDto convert2Dto(ProductLine productLine) {
        ProductLineDto dto = new ProductLineDto();
        dto.setId(productLine.getId());
        dto.setName(productLine.getName());
        dto.setDescription(productLine.getDescription());
        dto.setCategory(productLine.getCategory().getId());
        return dto;
    }


}
