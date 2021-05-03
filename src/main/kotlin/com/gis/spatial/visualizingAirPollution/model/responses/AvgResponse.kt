package com.gis.spatial.visualizingAirPollution.model.responses

class AvgResponse (val country: String, val avg: Double){

    init {
        country.split(" ")[0]
    }
}