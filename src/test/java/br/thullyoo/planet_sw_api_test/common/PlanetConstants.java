package br.thullyoo.planet_sw_api_test.common;

import br.thullyoo.planet_sw_api_test.model.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");

}
