package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.repository.ColorRepository;
import com.chikacow.pet_project.service.ColorService;
import com.chikacow.pet_project.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ProductService productService;

    public ColorServiceImpl(ColorRepository colorRepository, ProductService productService) {
        this.colorRepository = colorRepository;
        this.productService = productService;
    }

    @Override
    public Color saveColor(Color color) {
        return this.colorRepository.save(color);
    }

    @Override
    public List<Color> getAllColorByProductId(long id) {

        return this.colorRepository.findAllByProductId(id);
    }
}
