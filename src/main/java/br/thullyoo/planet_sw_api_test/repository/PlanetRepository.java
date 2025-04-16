package br.thullyoo.planet_sw_api_test.repository;

import br.thullyoo.planet_sw_api_test.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
