package com.gis.spatial.visualizingAirPollution.model.responses

class ResponseMessage (private val message: String){

    init {
        message.split(" ")[0]
    }
}