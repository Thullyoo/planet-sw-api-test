package br.thullyoo.planet_sw_api_test;

import br.thullyoo.planet_sw_api_test.model.Planet;
import br.thullyoo.planet_sw_api_test.model.builder.QueryBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.*;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/import_planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clear_planets.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlanetIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createPlanet_ReturnsCreated(){
        ResponseEntity<Planet> res = testRestTemplate.postForEntity("/planets", PLANET, Planet.class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(res.getBody()).isNotNull();
        Assertions.assertThat(res.getBody().getId()).isNotNull();
        Assertions.assertThat(res.getBody().getName()).isEqualTo(PLANET.getName());
        Assertions.assertThat(res.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
        Assertions.assertThat(res.getBody().getClimate()).isEqualTo(PLANET.getClimate());
    }

    @Test
    public void createPlanet_ReturnsPlanets(){
        ResponseEntity<Planet> res = testRestTemplate.getForEntity("/planets/1", Planet.class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody()).isNotNull();
        Assertions.assertThat(res.getBody().getId()).isNotNull();
        Assertions.assertThat(res.getBody()).isEqualTo(TATOOINE);

    }

    @Test
    public void getPlanetByName_ReturnsPlanet() {
        ResponseEntity<Planet> res = testRestTemplate.getForEntity("/planets/name/Alderaan", Planet.class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody()).isNotNull();
        Assertions.assertThat(res.getBody().getId()).isNotNull();
        Assertions.assertThat(res.getBody()).isEqualTo(ALDERAAN);

    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        ResponseEntity<Planet[]> res = testRestTemplate.getForEntity("/planets", Planet[].class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody()).hasSize(3);
        Assertions.assertThat(res.getBody()[0]).isEqualTo(TATOOINE);
        Assertions.assertThat(res.getBody()[1]).isEqualTo(ALDERAAN);
        Assertions.assertThat(res.getBody()[2]).isEqualTo(YAVINIV);
    }

    @Test
    public void listPlanets_ByClimate_ReturnsPlanets() {
        ResponseEntity<Planet[]> res = testRestTemplate.getForEntity("/planets?climate=" + YAVINIV.getClimate(), Planet[].class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody()).hasSize(1);
        Assertions.assertThat(res.getBody()[0]).isEqualTo(YAVINIV);
    }

    @Test
    public void listPlanets_ByTerrain_ReturnsPlanets() {
        ResponseEntity<Planet[]> res = testRestTemplate.getForEntity("/planets?terrain=" + YAVINIV.getTerrain(), Planet[].class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody()).hasSize(1);
        Assertions.assertThat(res.getBody()[0]).isEqualTo(YAVINIV);
    }

    @Test
    public void removePlanet_ReturnsNoContent() {
        ResponseEntity<Void> res = testRestTemplate.exchange("/planets/1", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(res.getBody()).isNull();
    }
}
