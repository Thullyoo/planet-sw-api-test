package br.thullyoo.planet_sw_api_test.service;

import br.thullyoo.planet_sw_api_test.model.Planet;
import br.thullyoo.planet_sw_api_test.repository.PlanetRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet){
        return planetRepository.save(planet);
    }
}
