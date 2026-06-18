package com.pluralsight.concerttracker.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pluralsight.concerttracker.models.Artist;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    

}