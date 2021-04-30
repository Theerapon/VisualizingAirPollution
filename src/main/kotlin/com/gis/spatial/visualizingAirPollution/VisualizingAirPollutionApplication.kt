package com.gis.spatial.visualizingAirPollution

import com.bedatadriven.jackson.datatype.jts.JtsModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class VisualizingAirPollutionApplication

fun main(args: Array<String>) {
	runApplication<VisualizingAirPollutionApplication>(*args)
}

@Bean
fun restTemplate(builder: RestTemplateBuilder): RestTemplate? {
	// Do any additional configuration here
	return builder.build()
}



