package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.SignatureProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignatureProductRepository extends JpaRepository<SignatureProduct, Long> {
    public List<SignatureProduct> findAllByArtistId(long id);

    public SignatureProduct findByProductId(long id);
}
