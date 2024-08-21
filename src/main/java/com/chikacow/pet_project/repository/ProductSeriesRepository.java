package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.ProductSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ProductSeriesRepository extends JpaRepository<ProductSeries, Long> {


}
