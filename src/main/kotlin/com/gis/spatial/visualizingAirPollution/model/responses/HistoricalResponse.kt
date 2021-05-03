package com.gis.spatial.visualizingAirPollution.model.responses

class HistoricalResponse (val country: String, val year: String, val pm: Double){

    init {
        country.split(" ")[0]
        year.split(" ")[0]
    }
}