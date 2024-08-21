package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.ProductSeries;

import java.io.IOException;
import java.util.List;

public interface ProductSeriesService {
    public void saveProductSeries(ProductSeries productSeries);

    public ProductSeries getProdSeriesById(long id);

    public List<ProductSeries> getAllProductSeries();

    public String getNameModified(String input, String prodLineName) throws IOException;

    public void deleteProductSeriesById(long id);
}
