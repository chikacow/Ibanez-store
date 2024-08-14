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
        return this.artistRepository.save(artist);
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
