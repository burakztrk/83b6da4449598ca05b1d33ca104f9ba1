package com.ozturkburak.outerworlds.repo

import com.ozturkburak.outerworlds.api.StationApi
import com.ozturkburak.outerworlds.model.response.StationDetail

interface StationRepository {
    suspend fun fetchStationList(): List<StationDetail>?
}

class StationRepositoryImpl(
    private val stationApi: StationApi
) : StationRepository {
    override suspend fun fetchStationList() = stationApi.fetchStationList()
}