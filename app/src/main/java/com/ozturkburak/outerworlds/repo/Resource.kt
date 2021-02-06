package com.ozturkburak.outerworlds.repo

import com.ozturkburak.outerworlds.model.response.StationDetail

sealed class Resource {
    data class Success(val data: List<StationDetail>) : Resource()
    data class Error(val message: String?) : Resource()
    object Loading : Resource()
}
