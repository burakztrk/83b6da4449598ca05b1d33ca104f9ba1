package com.ozturkburak.outerworlds.data.remote

import com.ozturkburak.outerworlds.data.entity.StationItemData

sealed class Resource {
    data class Success(val data: List<StationItemData>, val selectedPosition: Int? = null) :
        Resource()

    data class Error(val message: String?) : Resource()
    object Loading : Resource()
}