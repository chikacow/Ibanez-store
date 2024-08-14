package com.chikacow.pet_project.repository;

import com.chikacow.pet_project.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    public List<Artist> findAll();
}
