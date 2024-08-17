package com.chikacow.pet_project.service.implement;

import com.chikacow.pet_project.domain.Artist;
import com.chikacow.pet_project.repository.ArtistRepository;
import com.chikacow.pet_project.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Artist saveArtist(Artist artist) {
        Optional<Artist> container = this.artistRepository.findById(artist.getId());
        if (container.isEmpty()) {
            artist.setName("name");
            artist.setBio("bio");
            artist.setNationality("nation");
            artist.setSignatureModel("SIG");
            Artist saved = this.artistRepository.save(artist);
            saved.setName("");
            saved.setBio("");
            saved.setNationality("");
            saved.setSignatureModel("");
            return saved;
        } else {
            return this.artistRepository.save(artist);
        }
    }

    @Override
    public Artist getArtistById(long id) {
        Optional<Artist> container = this.artistRepository.findById(id);
        if (container.isEmpty()) {
            return null;
        }
        return container.get();
    }

    @Override
    public List<Artist> getAllArtist() {
        return this.artistRepository.findAll();
    }

    @Override
    public void deleteArtistById(long id) {
        this.artistRepository.deleteById(id);
    }
}
