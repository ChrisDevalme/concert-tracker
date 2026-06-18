package com.pluralsight.concerttracker.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pluralsight.concerttracker.models.Artist;

import java.util.List;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByGenreIgnoreCase(String genre);
    List<Artist> findByNameContainingIgnoreCase(String name);
}