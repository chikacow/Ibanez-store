package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Artist;
import com.chikacow.pet_project.domain.ProductSeries;

import java.io.IOException;
import java.util.List;

public interface ProductSeriesService {
    public ProductSeries saveProductSeries(ProductSeries productSeries);

    public ProductSeries getProdSeriesById(long id);

    public List<ProductSeries> getAllProductSeries();

    public String getNameModified(String input, String prodLineName) throws IOException;

    public void deleteProductSeriesById(long id);

    public ProductSeries getProductArtistSeries(Artist artist);
}
