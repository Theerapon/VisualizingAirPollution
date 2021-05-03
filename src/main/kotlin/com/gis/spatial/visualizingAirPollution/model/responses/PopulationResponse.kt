package com.gis.spatial.visualizingAirPollution.model.responses

class PopulationResponse (private val country: String, private val year: String, private val level: String, private val populationParam: Double){

    init {
        country.split(" ")[0]
        year.split(" ")[0]
        level.split(" ")[0]
    }
}