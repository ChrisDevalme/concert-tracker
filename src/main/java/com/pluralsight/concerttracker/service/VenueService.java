package com.pluralsight.concerttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.concerttracker.data.VenueRepository;
import com.pluralsight.concerttracker.models.Venue;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue addVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }


    public Venue getVenueById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    public long count() {
        return venueRepository.count();
    }

    public Venue updateCapacity(Long id, int capacity) {
        Venue venue = venueRepository.findById(id).orElse(null);

        if (venue == null) {
            return null;
        }

        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }

        venue.setCapacity(capacity);
        return venueRepository.save(venue);
    }


    public boolean deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            return false;
        }

        venueRepository.deleteById(id);
        return true;
    }

    public List<Venue> findVenuesByCity(String city) {
        return venueRepository.findByCityIgnoreCase(city);
    }

    public List<Venue> findVenuesByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Venue> findVenuesByMinimumCapacity(int capacity) {
        return venueRepository.findByCapacityGreaterThanEqual(capacity);
    }

    

    
}