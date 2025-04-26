package br.thullyoo.planet_sw_api_test.repository;

import br.thullyoo.planet_sw_api_test.model.Planet;
import br.thullyoo.planet_sw_api_test.model.builder.QueryBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void AfterEach(){
        PLANET.setId(null);
    }


    @Test
    public void createPlanet_withValidData_returnsPlanet(){
        Planet planet = planetRepository.save(PLANET);

        Planet res = testEntityManager.find(Planet.class, planet.getId());

        assertThat(res).isNotNull();
        assertThat(res.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(res.getName()).isEqualTo(PLANET.getName());
        assertThat(res.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_withInvalidData_throwsException() {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createPlanet_withExistingName_throwsException(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> res = planetRepository.findById(planet.getId());

        assertThat(res).isNotEmpty();
        assertThat(res.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty(){
        Optional<Planet> res = planetRepository.findById(1L);

        assertThat(res).isEmpty();

    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> res = planetRepository.findByName(planet.getName());

        assertThat(res).isNotEmpty();
        assertThat(res.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty(){
        Optional<Planet> res = planetRepository.findByName("namee");

        assertThat(res).isEmpty();

    }

    @Sql(scripts = "/import_planets.sql")
    @Test
    public void listPlanets_ReturnsFilteredPlanets(){
        Example<Planet> queryWithFilter = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));
        Example<Planet> queryWithoutFilter = QueryBuilder.makeQuery(new Planet());

        List<Planet> resWithoutFilter = planetRepository.findAll(queryWithoutFilter);
        List<Planet> resWithFilter = planetRepository.findAll(queryWithFilter);

        assertThat(resWithoutFilter).hasSize(3);
        assertThat(resWithFilter).hasSize(1);
        assertThat(resWithFilter.get(0)).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets(){
        Example<Planet> query = QueryBuilder.makeQuery(new Planet());

        List<Planet> res = planetRepository.findAll(query);

        assertThat(res).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_RemovesPlanetFromDatabase(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        planetRepository.deleteById(planet.getId());
        Planet res = testEntityManager.find(Planet.class, planet.getId());
        assertThat(res).isNull();

    }
    /*
    @Test
    public void removePlanet_WithUnexistingId_ThrowsException(){
        assertThatThrownBy(() -> planetRepository.deleteById(99L)).isInstanceOf(EmptyResultDataAccessException.class);
    }*/
}