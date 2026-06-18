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
}