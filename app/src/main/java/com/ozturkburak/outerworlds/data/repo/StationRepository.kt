package com.ozturkburak.outerworlds.data.repo

import com.ozturkburak.outerworlds.data.remote.StationApi
import com.ozturkburak.outerworlds.utils.Constants.Companion.STATION_WORLD_ID
import com.ozturkburak.outerworlds.utils.calculateDistance
import com.ozturkburak.outerworlds.data.local.StationDao
import com.ozturkburak.outerworlds.data.entity.StationEntity

interface StationRepository {
    suspend fun fetchStationList(): List<StationEntity>?

    suspend fun getCachedStationList(): List<StationEntity>?

    suspend fun getCurrentStation(): StationEntity

    suspend fun updateCurrentStation(newStation: StationEntity)

    suspend fun updateStation(newStation: StationEntity)

    suspend fun getFavoriteStationList(): List<StationEntity>?

    suspend fun targetStationsTotalEUS(): Int

    suspend fun isStationsStockFull() : Boolean

    fun getWorldStation() : StationEntity
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
        stationDao.getCurrentStation() ?: getWorldStation()

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

    override suspend fun getFavoriteStationList() = stationDao.getFavoriteList()

    override suspend fun targetStationsTotalEUS() =
        getCachedStationList()?.filter { it.stock != it.capacity }
            ?.sumByDouble { it.calculateDistance(getCurrentStation()) }?.toInt() ?: 0

    override suspend fun isStationsStockFull()= getCachedStationList()?.find { it.stock != it.capacity } == null

    override fun getWorldStation() : StationEntity = StationEntity(
        STATION_WORLD_ID,
        coordinateX = .0,
        coordinateY = .0,
        capacity = 0,
        stock = 0,
        need = 0,
        currentStation = true
    )
}