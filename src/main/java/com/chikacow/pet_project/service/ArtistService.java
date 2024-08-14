package com.chikacow.pet_project.service;

import com.chikacow.pet_project.domain.Artist;

import java.util.List;

public interface ArtistService {
    public Artist saveArtist(Artist artist);

    public Artist getArtistById(long id);

    public List<Artist> getAllArtist();

    public void deleteArtistById(long id);

}
