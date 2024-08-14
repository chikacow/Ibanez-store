package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.SignatureProduct;
import com.chikacow.pet_project.repository.SignatureProductRepository;
import com.chikacow.pet_project.service.SignatureProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignatureProductServiceImpl implements SignatureProductService {
    private final SignatureProductRepository signatureProductRepository;

    @Autowired
    public SignatureProductServiceImpl(SignatureProductRepository signatureProductRepository) {
        this.signatureProductRepository = signatureProductRepository;
    }


    @Override
    public SignatureProduct saveSignatureProduct(SignatureProduct signatureProduct) {
        return this.signatureProductRepository.save(signatureProduct);
    }

    @Override
    public List<SignatureProduct> getAllSignatureProductByArtistId(long id) {
        return this.signatureProductRepository.findAllByArtistId(id);
    }

    @Override
    public SignatureProduct getSignatureProductByProductId(long id) {
        return this.signatureProductRepository.findByProductId(id);
    }

    @Override
    public void deleteById(long id) {
        this.signatureProductRepository.deleteById(id);
    }
}
