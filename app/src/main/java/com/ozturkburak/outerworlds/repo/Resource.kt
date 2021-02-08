package com.ozturkburak.outerworlds.repo

import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData

sealed class Resource {
    data class Success(val data: List<StationItemData>, val selectedPosition: Int? = null) :
        Resource()

    data class Error(val message: String?) : Resource()
    object Loading : Resource()
}