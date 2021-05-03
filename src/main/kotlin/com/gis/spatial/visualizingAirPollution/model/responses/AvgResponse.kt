package com.gis.spatial.visualizingAirPollution.model.responses

class AvgResponse (val country: String, val avgParam: Double){

    init {
        country.split(" ")[0]
    }
}