package br.thullyoo.planet_sw_api_test.service;

import br.thullyoo.planet_sw_api_test.model.Planet;

import br.thullyoo.planet_sw_api_test.model.builder.QueryBuilder;
import br.thullyoo.planet_sw_api_test.repository.PlanetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.PLANET;
import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.INVALID_PLANET;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Test
    public void createPlanet_withValidData_returnsPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet planet = planetService.create(PLANET);

        assertThat(planet).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_withInvalidDate_throwException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_byExistingId_ReturnsPlanet() {
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET));

        Optional<Planet> res = planetService.get(1L);

        assertThat(res).isNotEmpty();
        assertThat(res.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_byUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Planet> res = planetService.get(1L);

        assertThat(res).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findByName("name")).thenReturn(Optional.of(PLANET));

        Optional<Planet> res = planetService.getByName("name");

        assertThat(res).isNotEmpty();
        assertThat(res.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        when(planetRepository.findByName("n")).thenReturn(Optional.empty());

        Optional<Planet> res = planetService.getByName("n");

        assertThat(res).isEmpty();
    }

    @Test
    public void listPlanet_returnAllPlanets(){
        List<Planet> planets = new ArrayList<>();
        planets.add(PLANET);

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> res = planetService.list(PLANET.getTerrain(),PLANET.getClimate());

        assertThat(res).isNotEmpty();
        assertThat(res.size()).isEqualTo(1);
        assertThat(res.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanet_returnNoPlanets(){
        when(planetRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        List<Planet> res = planetService.list(PLANET.getClimate(), PLANET.getTerrain());

        assertThat(res).isEmpty();
    }

    @Test
    public void removePlanet_withExistingId_doesNotThrowException(){
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_withUnexistingId_ThrowsException(){
        doThrow(RuntimeException.class).when(planetRepository).deleteById(1L);

        assertThatThrownBy(() -> planetService.remove(1L)).isInstanceOf(RuntimeException.class);
    }
}