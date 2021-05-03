package com.gis.spatial.visualizingAirPollution.model.responses

class HistoricalResponse (private val countryParam: String, private val yearParam: String, private val pmParam: Double){
    val country = countryParam.split(" ")[0]
    val year = yearParam.split(" ")[0]
    val pm = pmParam
}