package br.thullyoo.planet_sw_api_test.service;

import br.thullyoo.planet_sw_api_test.model.Planet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.thullyoo.planet_sw_api_test.repository.PlanetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.PLANET;
import static br.thullyoo.planet_sw_api_test.common.PlanetConstants.INVALID_PLANET;

import static org.mockito.Mockito.when;

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
}