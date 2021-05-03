package com.gis.spatial.visualizingAirPollution.model.responses

class AvgResponse (private val countryParam: String, private val avgParam: Double){
    val country = countryParam.split(" ")[0]
    val avg = avgParam
}