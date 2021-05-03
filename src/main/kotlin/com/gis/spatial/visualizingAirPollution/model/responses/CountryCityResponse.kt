package com.gis.spatial.visualizingAirPollution.model.responses

class CountryCityResponse(private val countryParam: String, private val cityParam: String){
    val country = countryParam.split(" ")[0]
    val city = cityParam.split(" ")[0]
}