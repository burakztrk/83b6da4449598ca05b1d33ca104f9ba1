package com.ozturkburak.outerworlds.data.entity

import com.ozturkburak.outerworlds.data.entity.StationEntity

data class StationItemData(
    val stockText: String?,
    val eus: Double?,
    val eusText: String?,
    val name: String?,
    val missionSuccess: Boolean = false,
    val data: StationEntity
)