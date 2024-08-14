package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.SignatureProduct;

import java.util.List;

public interface SignatureProductService {
    public SignatureProduct saveSignatureProduct(SignatureProduct signatureProduct);

    public List<SignatureProduct> getAllSignatureProductByArtistId(long id);

    public SignatureProduct getSignatureProductByProductId(long id);

    public void deleteById(long id);
}
