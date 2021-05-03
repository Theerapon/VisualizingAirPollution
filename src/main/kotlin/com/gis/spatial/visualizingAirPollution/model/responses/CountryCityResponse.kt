package com.gis.spatial.visualizingAirPollution.model.responses

class CountryCityResponse(val country: String, val city: String){

    init {
        country.split(" ")[0]
        city.split(" ")[0]
    }

}