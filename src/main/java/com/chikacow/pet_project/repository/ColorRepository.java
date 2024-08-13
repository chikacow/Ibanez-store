package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    // Custom query to find all colors by productId
    //native query approach
    //Query(value = "SELECT c.* FROM color c JOIN product_color pc ON c.id = pc.color_id WHERE pc.product_id = :productId", nativeQuery = true)

    //JPQL approach
    //from non-owning side
    //@Query("SELECT p.colors FROM Product p WHERE p.id = :productId")
    //from owning side (preferable)
    @Query("SELECT c FROM Color c JOIN c.productList p WHERE p.id = :productId")
    List<Color> findAllByProductId(@Param("productId") Long productId);

}
