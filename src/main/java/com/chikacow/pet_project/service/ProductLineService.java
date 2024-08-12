package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.ProductLine;
import com.chikacow.pet_project.dto.ProductLineDto;

import java.util.List;

public interface ProductLineService {
    public ProductLine saveProductLine(ProductLine productLine);

    public ProductLine getByProdLineId(long id);

    public List<ProductLine> getAllProdLine();

    public void deleteById(long id);
    public void deleteAll();
    public ProductLine dtoConvert(ProductLineDto productLineDto);

    public ProductLineDto convert2Dto(ProductLine productLine);
}
