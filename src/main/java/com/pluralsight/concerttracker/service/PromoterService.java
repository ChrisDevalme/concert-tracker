package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.PromoterRepository;
import com.pluralsight.concerttracker.models.Promoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoterService {

    private final PromoterRepository promoterRepository;

    @Autowired
    public PromoterService(PromoterRepository promoterRepository) {
        this.promoterRepository = promoterRepository;
    }

    public Promoter addPromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    public List<Promoter> getAllPromoters() {
        return promoterRepository.findAll();
    }

    public Promoter getPromoterById(Long id) {
        return promoterRepository.findById(id).orElse(null);
    }

    public List<Promoter> findPromotersByName(String name) {
        return promoterRepository.findByNameContainingIgnoreCase(name);
    }

    public boolean deletePromoter(Long id) {
        if (!promoterRepository.existsById(id)) {
            return false;
        }

        promoterRepository.deleteById(id);
        return true;
    }

    public long count() {
        return promoterRepository.count();
    }
}