package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.Artist;
import com.chikacow.pet_project.domain.ProductSeries;
import com.chikacow.pet_project.repository.ProductSeriesRepository;
import com.chikacow.pet_project.service.ProductSeriesService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSeriesServiceImpl implements ProductSeriesService {
    private final ProductSeriesRepository productSeriesRepository;

    public ProductSeriesServiceImpl(ProductSeriesRepository productSeriesRepository) {
        this.productSeriesRepository = productSeriesRepository;
    }

    @Override
    public ProductSeries saveProductSeries(ProductSeries productSeries) {
        this.productSeriesRepository.save(productSeries);

        return productSeries;
    }

    @Override
    public ProductSeries getProdSeriesById(long id) {
        Optional<ProductSeries> container = this.productSeriesRepository.findById(id);
        if (container.isEmpty()) {
            System.out.println("no id founded from service");
            return null;
        }
        return container.get();
    }

    @Override
    public List<ProductSeries> getAllProductSeries() {
        return this.productSeriesRepository.findAll();
    }

    @Override
    public String getNameModified(String input, String prodLineName) throws IOException {

        if (input.split(" ").length <= 1) {
            return prodLineName + " " + input;
        } else {

            if (input.split(" ")[0].equals(prodLineName)) {
                System.out.println("good name");
                return input;
            } else {
                System.out.println("the fak name? wrong product line");
                throw new IOException("cant accept this");
            }
        }

    }

    @Override
    public void deleteProductSeriesById(long id) {
        this.productSeriesRepository.deleteById(id);
    }

    @Override
    public ProductSeries getProductArtistSeries(Artist artist) {
        ProductSeries productSeries = new ProductSeries();
        productSeries.setName(artist.getSeriesCode());
        productSeries.setImage(artist.getImage());

        return productSeries;
    }
}
