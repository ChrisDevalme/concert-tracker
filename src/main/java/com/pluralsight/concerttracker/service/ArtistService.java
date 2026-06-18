package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ArtistRepository;
import com.pluralsight.concerttracker.models.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public long count() {
        return artistRepository.count();
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    public List<Artist> findArtistsByGenre(String genre) {
        return artistRepository.findByGenreIgnoreCase(genre);
    }
    public List<Artist> findArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }
    public Artist updateGenre(Long id, String genre) {
        Artist artist = artistRepository.findById(id).orElse(null);

        if (artist == null) {
            return null;
        }

        artist.setGenre(genre);
        return artistRepository.save(artist);
    }
    public boolean deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            return false;
        }
        artistRepository.deleteById(id);
        return true;
    }
}