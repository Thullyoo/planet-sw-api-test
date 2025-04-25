package br.thullyoo.planet_sw_api_test;

import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.PLANET;

import br.thullyoo.planet_sw_api_test.model.Planet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
}
