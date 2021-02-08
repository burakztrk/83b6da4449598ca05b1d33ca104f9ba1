package com.ozturkburak.outerworlds.features.stationlist

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.database.entity.StationEntity
import com.ozturkburak.outerworlds.features.stationlist.station.list.AdapterDataMapper
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.ozturkburak.outerworlds.repo.Resource
import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.StationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StationListViewModel(
    private val stationRepository: StationRepository,
    private val shipRepository: ShipRepository
) : ViewModel() {

    val shipInfoObservable = ObservableField<ShipEntity>()
    val currentStationObservable = ObservableField<StationEntity>()
    val timerObservable = ObservableField<String>()

    private val _sliderAdapterLiveData = MutableLiveData<Resource>()
    val sliderAdapterLiveData: LiveData<Resource> get() = _sliderAdapterLiveData

    private val _favoriteAdapterLiveData = MutableLiveData<Resource>()
    val favoriteAdapterLiveData: LiveData<Resource> get() = _favoriteAdapterLiveData

    private var timerIsActive = true
    private var counter = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateUIShipInfo()
            updateFavoriteStations()
        }
        getStations()
    }

    private fun getStations() {
        viewModelScope.launch {
            _sliderAdapterLiveData.value = Resource.Loading
            runCatching {
                stationRepository.fetchStationList()?.takeIf { it.isNotEmpty() }?.let { list ->
                    currentStationObservable.set(stationRepository.getCurrentStation())
                    _sliderAdapterLiveData.value = Resource.Success(getAdapterData(list))
                    initTimer()
                } ?: run {
                    _sliderAdapterLiveData.value = Resource.Error("Network Hatası - EMPTY LIST")
                }
            }.onFailure {
                it.printStackTrace()
                _sliderAdapterLiveData.value = Resource.Error(it.message)
            }
        }
    }

    private suspend fun getAdapterData(list: List<StationEntity>) = AdapterDataMapper(
        stationList = list,
        stationRepository.getCurrentStation(),
        shipRepository.getShipData()
    ).execute()


    fun onStationButtonClick(stationItem: StationItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            updateEUS(stationItem)
            updateUGS(stationItem)
            updateUIShipInfo()

            updateCurrentLocation(stationItem.data)
            updateAdapterData(stationItem)
        }
    }

    private suspend fun updateUGS(stationItem: StationItemData) {
        shipRepository.saveShipData(shipRepository.getShipData().apply {
            this.capacity -= stationItem.data.need ?: 0
        })

        stationRepository.updateStation(stationItem.data.apply {
            stock = capacity
            need = 0
        })
    }

    private suspend fun updateEUS(stationItem: StationItemData) {
        val newShipData = shipRepository.getShipData().apply {
            stationItem.eus?.toInt()?.let {
                speed -= it
            }
        }
        shipRepository.saveShipData(newShipData)
    }

    private suspend fun updateAdapterData(selected: StationItemData? = null) {
        stationRepository.getCachedStationList()?.let {
            _sliderAdapterLiveData.postValue(Resource.Success(getAdapterData(it), selected))
        }
    }

    private suspend fun updateCurrentLocation(newStation: StationEntity) {
        stationRepository.updateCurrentStation(newStation)
        currentStationObservable.set(newStation)
    }

    private suspend fun updateUIShipInfo() {
        shipInfoObservable.set(shipRepository.getShipData())
    }

    fun onStationFavoriteClick(stationItem: StationItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            stationRepository.updateStation(stationItem.data.apply {
                isFavorite = isFavorite.not()
            })
            updateAdapterData(stationItem)
            updateFavoriteStations()
        }

    }

    private suspend fun updateFavoriteStations() {
        stationRepository.getFavoriteStationList()?.let {
            _favoriteAdapterLiveData.postValue(Resource.Success(getAdapterData(it)))
        }
    }

    private fun initTimer() {
        viewModelScope.launch {
            while (timerIsActive) {
                delay(1000L)
                timerObservable.set("${counter++}s")
            }
        }
    }

    override fun onCleared() {
        timerIsActive = false
        super.onCleared()
    }

}