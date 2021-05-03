package com.gis.spatial.visualizingAirPollution.model.responses

class PopulationResponse (val country: String, val year: String, val level: String, val populationParam: Double){

    init {
        country.split(" ")[0]
        year.split(" ")[0]
        level.split(" ")[0]
    }
}