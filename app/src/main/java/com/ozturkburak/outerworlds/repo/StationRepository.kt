package com.ozturkburak.outerworlds.repo

import com.ozturkburak.outerworlds.api.StationApi
import com.ozturkburak.outerworlds.base.Constants
import com.ozturkburak.outerworlds.database.dao.StationDao
import com.ozturkburak.outerworlds.database.entity.StationEntity

interface StationRepository {
    suspend fun fetchStationList(): List<StationEntity>?

    suspend fun getWordStation(): StationEntity
}

class StationRepositoryImpl(
    private val stationApi: StationApi,
    private val stationDao: StationDao
) : StationRepository {
    override suspend fun fetchStationList(): List<StationEntity> {
        val list = stationApi.fetchStationList()
        stationDao.insert(list)
        return list
    }

    override suspend fun getWordStation(): StationEntity =
        stationDao.get(Constants.STATION_WORLD_ID) ?: StationEntity(
            Constants.STATION_WORLD_ID,
            .0,
            .0,
            0,
            0,
            0
        )
}