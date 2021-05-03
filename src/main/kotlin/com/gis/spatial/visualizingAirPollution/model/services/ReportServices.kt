package com.gis.spatial.visualizingAirPollution.model.services

import com.gis.spatial.visualizingAirPollution.model.responses.AvgResponse
import com.gis.spatial.visualizingAirPollution.model.responses.CountryCityResponse
import com.gis.spatial.visualizingAirPollution.model.responses.HistoricalResponse
import com.gis.spatial.visualizingAirPollution.model.responses.PopulationResponse
import com.gis.spatial.visualizingAirPollution.repositories.AirPollutionPM25Repository
import org.springframework.stereotype.Service

@Service
class ReportServices(private val airpollutionpm25Repository: AirPollutionPM25Repository) {

    // 4.1 List country and city names whose PM 2.5 values are greater than $value in $year.
    fun getListOfCountryAndCityByPm25InYear(pm: Double, year: String): List<CountryCityResponse> {
        return airpollutionpm25Repository.findCountryAndCityByPm25InYear(pm, year)
    }

    // 4.2 Calculate the AVG(PM 2.5) by country (show the results in a decreasing order).
    fun getListAvg(): List<AvgResponse> {
        return airpollutionpm25Repository.findAVGGroupByCountry()
    }

    // 4.2 Calculate the AVG(PM 2.5) by country (show the results in a decreasing order).
    fun getListAvgByCountry(country: String): List<AvgResponse> {
        return airpollutionpm25Repository.findAVGByCountry(country)
    }

    // 4.3 Given a <country_input> from the user, show a historical PM 2.5 values by year.
    fun getListHistoricalpm25ByCountry(country: String): List<HistoricalResponse> {
        return airpollutionpm25Repository.findHistoricalByCountry(country)
    }

    // 4.4 Given a <year_input> and an input of <color_pm25> level of health concern
    // from the user, calculate a total of the affected population (in number).
    fun getListTotalPopulation(year: String, color: String): List<PopulationResponse> {
        return airpollutionpm25Repository.findTotalPopulationByYearAndColor(year, color)
    }


}