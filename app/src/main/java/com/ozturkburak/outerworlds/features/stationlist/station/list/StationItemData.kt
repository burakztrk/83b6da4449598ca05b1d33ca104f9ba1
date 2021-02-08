package com.ozturkburak.outerworlds.features.stationlist.station.list

import com.ozturkburak.outerworlds.database.entity.StationEntity

data class StationItemData(
    val stockText: String?,
    val eus: Double?,
    val eusText: String?,
    val name: String?,
    val missionSuccess: Boolean = false,
    val data: StationEntity
)