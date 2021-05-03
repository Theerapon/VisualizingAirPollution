package com.gis.spatial.visualizingAirPollution.model.responses

class AvgResponse (private val country: String, private val avgParam: Double){

    init {
        country.split(" ")[0]
    }
}