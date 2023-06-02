package com.irrigation.irrigation.services;

import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.repositories.LandRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LandService {
    private final LandRepository landRepository;

    public LandService(LandRepository landRepository) {
        this.landRepository = landRepository;
    }


    public List<land> findAll() {
        List<land> lands = new ArrayList<>();
        landRepository.findAll().forEach(lands::add);

        return lands;
    }
    public  land create(land l) {
        return landRepository.save(l);
    }



    public Optional<land> findById(Long id) {
        return landRepository.findById(id);
    }

    public land update(land landToUpdate) {
        return landRepository.save(landToUpdate);
    }

    public void delete(Long id) {
        landRepository.deleteById(id);
    }



}
