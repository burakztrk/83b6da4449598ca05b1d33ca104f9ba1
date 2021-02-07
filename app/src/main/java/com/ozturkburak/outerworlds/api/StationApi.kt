package com.ozturkburak.outerworlds.api

import com.ozturkburak.outerworlds.database.entity.StationEntity
import retrofit2.http.GET

interface StationApi {
    @GET("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun fetchStationList(): List<StationEntity>
}