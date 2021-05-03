package com.gis.spatial.visualizingAirPollution.model.responses

class CountryCityResponse(private val country: String, private val city: String){

    init {
        country.split(" ")[0]
        city.split(" ")[0]
    }

}