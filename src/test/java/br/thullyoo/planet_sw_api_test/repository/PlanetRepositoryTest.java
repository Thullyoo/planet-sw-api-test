package br.thullyoo.planet_sw_api_test.repository;

import br.thullyoo.planet_sw_api_test.model.Planet;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.swing.text.html.Option;

import java.util.Optional;

import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.PLANET;
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
}