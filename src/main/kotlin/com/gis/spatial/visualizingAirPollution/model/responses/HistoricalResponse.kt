package com.gis.spatial.visualizingAirPollution.model.responses

class HistoricalResponse (private val country: String, private val year: String, private val pmParam: Double){

    init {
        country.split(" ")[0]
        year.split(" ")[0]
    }
}