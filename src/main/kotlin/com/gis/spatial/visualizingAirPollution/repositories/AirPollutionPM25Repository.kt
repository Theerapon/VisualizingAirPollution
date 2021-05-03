package com.gis.spatial.visualizingAirPollution.repositories

import com.vividsolutions.jts.geom.Geometry
import com.gis.spatial.visualizingAirPollution.model.entities.AirPollutionPM25
import com.gis.spatial.visualizingAirPollution.model.responses.AvgResponse
import com.gis.spatial.visualizingAirPollution.model.responses.CountryCityResponse
import com.gis.spatial.visualizingAirPollution.model.responses.HistoricalResponse
import com.gis.spatial.visualizingAirPollution.model.responses.PopulationResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AirPollutionPM25Repository : JpaRepository<AirPollutionPM25, Int> {

    // 4.1 List country and city names whose PM 2.5 values are greater than $value in $year.
    @Query(value = "SELECT new com.gis.spatial.visualizingAirPollution.model.responses.CountryCityResponse(a.country, a.city) FROM AirPollutionPM25 a WHERE a.pm25 > ?1 AND a.year = ?2")
    fun findCountryAndCityByPm25InYear(@Param("value") pm: Double, @Param("year") year: String): MutableList<CountryCityResponse>

    // 4.2 Calculate the AVG(PM 2.5) by country (show the results in a decreasing order).
    @Query(value = "SELECT new com.gis.spatial.visualizingAirPollution.model.responses.AvgResponse(a.country, AVG(a.pm25))  FROM AirPollutionPM25 a GROUP BY a.country ORDER BY Avg(a.pm25) DESC")
    fun findAVGGroupByCountry(): MutableList<AvgResponse>

    @Query(value = "SELECT new com.gis.spatial.visualizingAirPollution.model.responses.AvgResponse(a.country, AVG(a.pm25))  FROM AirPollutionPM25 a WHERE a.country = ?1 GROUP BY a.country")
    fun findAVGByCountry(@Param("country") country: String): MutableList<AvgResponse>

    // 4.3 Given a <country_input> from the user, show a historical PM 2.5 values by year.
    @Query(value = "SELECT new com.gis.spatial.visualizingAirPollution.model.responses.HistoricalResponse(a.country, a.year, a.pm25) FROM AirPollutionPM25 a WHERE a.country = ?1  ORDER BY a.year")
    fun findHistoricalByCountry(@Param("country") country: String): MutableList<HistoricalResponse>

    // 4.4 Given a <year_input> and an input of <color_pm25> level of health concern
    // from the user, calculate a total of the affected population (in number).
    @Query(value = "SELECT new com.gis.spatial.visualizingAirPollution.model.responses.PopulationResponse(a.country, a.year, concat(a.color_pm25, ' ',a.conc_pm25), SUM(a.population)) FROM AirPollutionPM25 a WHERE a.year = ?1 AND a.color_pm25 = ?2 GROUP BY a.country, a.year, a.color_pm25, a.conc_pm25, a.population" )
    fun findTotalPopulationByYearAndColor(@Param("year") year: String, @Param("color") color: String): MutableList<PopulationResponse>

    // DISTINCT For Year Drop Down
    @Query(value = "SELECT DISTINCT a.year FROM AirPollutionPM25 a ORDER BY a.year")
    fun distinctYear(): MutableList<String>

    // DISTINCT For Country Drop Down
    @Query(value = "SELECT DISTINCT a.country FROM AirPollutionPM25 a ORDER BY a.country")
    fun distinctCountry(): MutableList<String>

    // DISTINCT For Level Color PM 2.5 Drop Down
    @Query(value = "SELECT DISTINCT concat(a.color_pm25, ' ',a.conc_pm25) FROM AirPollutionPM25 a ORDER BY 1")
    fun distinctColor(): MutableList<String>

    // 5.1 Given a <year_input> from the user, visualize all the city points of all countries.
    // return AirPollutionPM25 for Map model to marker
    @Query(value = "SELECT a FROM AirPollutionPM25 a WHERE a.year = ?1")
    fun findCityByYear(@Param("year") year: String): MutableList<AirPollutionPM25>

    // 5.2 Visualize the 50 closest city points to City.
    @Query(value = "SELECT * FROM AirPollutionPM25 a WHERE a.city != ?1 ORDER BY a.Geom.STDistance(?2)", nativeQuery = true)
    fun findCityDistance(@Param("city") city: String, @Param("geom") geom: Geometry): MutableList<AirPollutionPM25>

    // find city point for 5.2
    @Query(value = "SELECT a FROM AirPollutionPM25 a WHERE a.city = ?1")
    fun findCityPoint(@Param("city") city: String): AirPollutionPM25

    // find Position (lat, long) city each country neighbor on year
    @Query(value = "SELECT a FROM AirPollutionPM25 a WHERE a.country in (?1) AND a.year = ?2")
    fun findCitiesInCountiesOnYear(@Param("country") country: ArrayList<String>, @Param("year") year: String): MutableList<AirPollutionPM25>

    // find country that have the most count of city on year
    @Query(value = "select a.country FROM AirPollutionPM25 a where a.year = ?1 group by a.country order by count(a.city) DESC")
    fun findCountriesHaveMostCitiesInYear(@Param("year") year: String): MutableList<String>

    // find city have low income on year
    @Query(value = "select a from AirPollutionPM25 a where a.wbinc16_text='low income' and a.year = ?1")
    fun findCitiesHaveLowIncome(@Param("year") year: String): MutableList<AirPollutionPM25>
}