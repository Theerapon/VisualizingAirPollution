package com.gis.spatial.visualizingAirPollution.model.responses

class ResponseMessage (private val messageParam: String){
    val message = messageParam.split(" ")[0]
}