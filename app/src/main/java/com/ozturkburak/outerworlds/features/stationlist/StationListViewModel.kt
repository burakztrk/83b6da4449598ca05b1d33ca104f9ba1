package com.ozturkburak.outerworlds.features.stationlist

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.database.entity.StationEntity
import com.ozturkburak.outerworlds.features.stationlist.station.list.AdapterDataMapper
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.ozturkburak.outerworlds.repo.Resource
import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.StationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationListViewModel(
    private val stationRepository: StationRepository,
    private val shipRepository: ShipRepository
) : ViewModel() {

    val shipInfoObservable = ObservableField<ShipEntity>()
    val currentStationObservable = ObservableField<StationEntity>()

    init {
        updateUI()
    }

    fun getStationList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        runCatching {
            stationRepository.fetchStationList()?.takeIf { it.isNotEmpty() }?.let { list ->
                emit(Resource.Success(getAdapterData(list)))
                currentStationObservable.set(stationRepository.getWordStation())
            } ?: run {
                emit(Resource.Error("Network Hatası - EMPTY LIST"))
            }
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message))
        }
    }

    private suspend fun getAdapterData(list: List<StationEntity>) = AdapterDataMapper(
        stationList = list,
        stationRepository.getWordStation(),
        shipRepository.getShipData()
    ).execute()


    fun onStationTravelClick(stationItem: StationItemData) {
        currentStationObservable.set(stationItem.data)
    }

    fun onStationFavoriteClick(data: StationItemData) {
        TODO("Not yet implemented")
    }


    private fun updateUI() {
        viewModelScope.launch {
            shipInfoObservable.set(shipRepository.getShipData())
        }
    }
}