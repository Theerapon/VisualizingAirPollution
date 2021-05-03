package com.gis.spatial.visualizingAirPollution.model.responses

class ResponseMessage (val message: String){

    init {
        message.split(" ")[0]
    }
}