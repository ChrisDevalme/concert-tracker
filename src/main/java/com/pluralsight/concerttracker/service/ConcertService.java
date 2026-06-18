package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ConcertRepository;
import com.pluralsight.concerttracker.models.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    @Autowired
    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public Concert addConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public long count() {
        return concertRepository.count();
    }

    public Concert getConcertById(Long id) {
        return concertRepository.findById(id).orElse(null);
    }

    public Concert updateTicketPrice(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative.");
        }
        Concert concert = concertRepository.findById(id).orElse(null);
        if (concert == null) {
            return null;
        }
        concert.setTicketPrice(newPrice);
        return concertRepository.save(concert);
    }

    public Concert updateTicketsSold(Long id, int ticketsSold) {
        if (ticketsSold < 0) {
            throw new IllegalArgumentException("Tickets sold cannot be negative.");
        }

        Concert concert = concertRepository.findById(id).orElse(null);

        if (concert == null) {
            return null;
        }

        if (ticketsSold > concert.getVenue().getCapacity()) {
            throw new IllegalArgumentException("Tickets sold cannot exceed venue capacity.");
        }

        concert.setTicketsSold(ticketsSold);
        return concertRepository.save(concert);
    }

    public boolean deleteConcert(Long id) {
        if (!concertRepository.existsById(id)) {
            return false;
        }

        concertRepository.deleteById(id);
        return true;
    }

    public List<Concert> findConcertsByYear(int year) {
        return concertRepository.findByConcertYear(year);
    }

    public List<Concert> findConcertsByArtist(String artistName) {
        return concertRepository.findByArtistName(artistName);
    }

    public List<Concert> findConcertsByVenue(String venueName) {
        return concertRepository.findByVenueName(venueName);
    }

    public List<Concert> findConcertsByCity(String city) {
        return concertRepository.findByCity(city);
    }

    public List<Concert> findConcertsByMaxPrice(double maxPrice) {
        return concertRepository.findByTicketPriceLessThanEqual(maxPrice);
    }

    public List<Concert> findConcertsByPriceRange(double minPrice, double maxPrice) {
        return concertRepository.findByTicketPriceBetween(minPrice, maxPrice);
    }

    public List<Concert> advancedSearch(double maxPrice, int earliestYear) {
        return concertRepository.advancedSearch(maxPrice, earliestYear);
    }


}