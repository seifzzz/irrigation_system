package services;

import land.land;
import repositories.LandRepository;

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

    public land create(land land) {
        return landRepository.save(land);
    }

    public List<land> findAll() {
        List<land> lands = new ArrayList<>();
        landRepository.findAll().forEach(lands::add);

        return lands;
    }

    public Optional<land> findById(String id) {
        return landRepository.findById(id);
    }

    public land update(land landToUpdate) {
        return landRepository.save(landToUpdate);
    }


}
