package com.gis.spatial.visualizingAirPollution.model.responses

class PopulationResponse (private val countryParam: String, private val yearParam: String, private val levelParam: String, private val populationParam: Double){
    val country = countryParam.split(" ")[0]
    val year = yearParam.split(" ")[0]
    val level = levelParam.split(" ")[0]
    val population = populationParam
}