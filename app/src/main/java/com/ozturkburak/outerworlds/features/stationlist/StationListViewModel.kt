package com.ozturkburak.outerworlds.features.stationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ozturkburak.outerworlds.repo.Resource
import com.ozturkburak.outerworlds.repo.StationRepository
import kotlinx.coroutines.Dispatchers

class StationListViewModel(
    private val stationRepository: StationRepository
) : ViewModel() {

    fun getStationList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        runCatching {
            emit(stationRepository.fetchStationList()?.takeIf {
                it.isNotEmpty()
            }?.let {
                Resource.Success(it)
            } ?: run {
                Resource.Error("Liste Boş")
            })
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message))
        }
    }
}