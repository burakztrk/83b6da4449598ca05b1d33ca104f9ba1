package com.ozturkburak.outerworlds.repo

import com.ozturkburak.outerworlds.api.StationApi
import com.ozturkburak.outerworlds.base.Constants.Companion.STATION_WORLD_ID
import com.ozturkburak.outerworlds.database.dao.StationDao
import com.ozturkburak.outerworlds.database.entity.StationEntity

interface StationRepository {
    suspend fun fetchStationList(): List<StationEntity>?

    suspend fun getCachedStationList(): List<StationEntity>?

    suspend fun getCurrentStation(): StationEntity

    suspend fun updateCurrentStation(newStation: StationEntity)

    suspend fun updateStation(newStation: StationEntity)

    suspend fun getFavoriteStationList(): List<StationEntity>?
}

class StationRepositoryImpl(
    private val stationApi: StationApi,
    private val stationDao: StationDao
) : StationRepository {
    override suspend fun fetchStationList(): List<StationEntity> {
        val list = stationApi.fetchStationList()
        list.find { it.name == STATION_WORLD_ID }?.currentStation = true
        stationDao.insert(list)
        return list
    }

    override suspend fun getCachedStationList(): List<StationEntity>? = stationDao.getList()

    override suspend fun getCurrentStation(): StationEntity =
        stationDao.getCurrentStation() ?: defaultStation()

    override suspend fun updateCurrentStation(newStation: StationEntity) {
        stationDao.getCurrentStation()?.let {
            stationDao.update(it.apply {
                currentStation = false
            })
        }
        stationDao.update(newStation.apply {
            currentStation = true
        })
    }

    override suspend fun updateStation(newStation: StationEntity) = stationDao.update(newStation)

    override suspend fun getFavoriteStationList()= stationDao.getFavoriteList()


    private fun defaultStation() = StationEntity(
        STATION_WORLD_ID,
        coordinateX = .0,
        coordinateY = .0,
        capacity = 0,
        stock = 0,
        need = 0,
        currentStation = true
    )
}