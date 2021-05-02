package com.gis.spatial.visualizingAirPollution.model.services

import com.gis.spatial.visualizingAirPollution.extensions.toFormat
import com.gis.spatial.visualizingAirPollution.extensions.toMarker
import com.gis.spatial.visualizingAirPollution.model.entities.Position
import com.gis.spatial.visualizingAirPollution.model.responses.MarkerResponse
import com.gis.spatial.visualizingAirPollution.repositories.AirPollutionPM25Repository
import com.gis.spatial.visualizingAirPollution.repositories.WorldRepository
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.GeometryFactory
import org.springframework.stereotype.Service

@Service
class MapVisualizingServices(
    private val airpollutionpm25Repository: AirPollutionPM25Repository,
    private val worldRepository: WorldRepository
) {

    // 5.1 Given a <year_input> from the user, visualize all the city points of all countries.
    fun findCityPositionByYear(year: String): List<MarkerResponse> {
        return airpollutionpm25Repository
            .findCityByYear(year)
            .map { model -> model.toMarker() }
    }

    // 5.2 Visualize the 50 closest city points to Bangkok.
    fun findClosetCityPosition(city: String): List<MarkerResponse> {
        var result = airpollutionpm25Repository.findCityPoint(city)
        return airpollutionpm25Repository
            .findCityDistance(city ,result.geom!!)
            .subList(0,50)
            .map{ model -> model.toMarker() }
    }

    // 5.3 Visualize all the city points of Thailand’s neighboring countries in 2018. By Year
    fun findThailandNeighborPositionByYear(year: String): List<MarkerResponse> {

        //find geom Thailand
        var thaiGeom = worldRepository.findCountry("Thailand")

        //find Name Thailand neighbor
        var neighborName = arrayListOf<String>()
        worldRepository.findListNeighbor(thaiGeom.geom!!).map { model -> neighborName.add(model.name!!) }

        //find Position (lat, long) city each country neighbor on year
        return airpollutionpm25Repository
            .findCitiesInCountiesOnYear(neighborName, year)
            .map { model -> model.toMarker() }
    }

    // 5.4 Visualize the four points of MBR covering all city points in Thailand in 2009.
    fun findMBRPositionByYear(year: String): List<Position> {
        //find all cities in Thailand on year
        var thaiGeom = airpollutionpm25Repository.findCitiesInCountiesOnYear(arrayListOf("Thailand"), year)

        //all geom each counties in Thailand
        var all = arrayListOf<Geometry>()
        thaiGeom.map { model -> all.add(model.geom!!) }

        //List of Geometry
        var multiGeom = GeometryFactory().buildGeometry(all)

        //Position marker
        return multiGeom.envelope.coordinates.map { model -> model.toFormat() }
    }

    // 5.5 Visualize all city points of countries having the highest no. of city points in 2011.
    fun findAllHighestCityPointsPositionByYear(year: String): List<MarkerResponse> {

        //list of countries have the most count of cities on year
        var countriesList = airpollutionpm25Repository.findCountriesHaveMostCitiesInYear(year)

        //find all cities in First Country that have the most count of cities on year
        var cities = airpollutionpm25Repository.findCitiesInCountiesOnYear(arrayListOf(countriesList.first()), year)

        //Position marker
        return cities.map { model -> model.toMarker() }
    }

    // 5.6 Given a <year_input> from the user, visualize all the city points which are
    // considered as “low income” (as specified in column wbinc16_text).
    fun findCitiesHaveLowIncome(year: String): List<MarkerResponse> {
        return airpollutionpm25Repository
            .findCitiesHaveLowIncome(year)
            .map { model -> model.toMarker() }
    }
}