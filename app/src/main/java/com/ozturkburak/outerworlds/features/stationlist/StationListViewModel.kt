package com.ozturkburak.outerworlds.features.stationlist

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.base.ResourcesProvider
import com.ozturkburak.outerworlds.base.Task
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.database.entity.StationEntity
import com.ozturkburak.outerworlds.features.stationlist.station.GameManager
import com.ozturkburak.outerworlds.features.stationlist.station.Timer
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.ozturkburak.outerworlds.repo.Resource
import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.StationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationListViewModel(
    private val stationRepository: StationRepository,
    private val shipRepository: ShipRepository,
    private val resourcesProvider: ResourcesProvider,
    private val gameManager: GameManager
) : ViewModel() {

    val shipInfoObservable = ObservableField<ShipEntity>()
    val currentStationObservable = ObservableField<StationEntity>()
    val timerObservable = ObservableField<String>()
    private val timer: Timer = Timer(this)

    private val _sliderAdapterLiveData = MutableLiveData<Resource>()
    val sliderAdapterLiveData: LiveData<Resource> get() = _sliderAdapterLiveData

    private val _favoriteAdapterLiveData = MutableLiveData<Resource>()
    val favoriteAdapterLiveData: LiveData<Resource> get() = _favoriteAdapterLiveData

    private val _finishGameLiveData = MutableLiveData<String>()
    val finishGameLiveData: LiveData<String> get() = _finishGameLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateUIShipInfo()
            updateFavoriteStations()
            initTimer()
        }
        getStations()
    }

    private fun getStations() {
        viewModelScope.launch {
            _sliderAdapterLiveData.value = Resource.Loading
            runCatching {
                val resource = gameManager.getStations()
                if (resource is Resource.Success) {
                    currentStationObservable.set(stationRepository.getCurrentStation())
                    startTimer()
                }
                _sliderAdapterLiveData.value = resource
            }.onFailure {
                it.printStackTrace()
                _sliderAdapterLiveData.value = Resource.Error(it.message)
            }
        }
    }

    private suspend fun initTimer() {
        timer.run {
            maxCounter = gameManager.getTimerMaxCounter()
            onTick = Task {
                timerObservable.set("${it}s")
            }
            onEndTime = Task {
                viewModelScope.launch(Dispatchers.IO) {
                    gameManager.checkShipAndStationEquipmentOrError()?.let {
                        returnTheWorld(it)
                        return@launch
                    }

                    shipRepository.saveShipData(shipRepository.getShipData().apply {
                        health -= 10
                    })
                    updateUIShipInfo()
                }
            }
        }
    }

    fun onStationButtonClick(stationItem: StationItemData) {
        viewModelScope.launch(Dispatchers.IO) {

            gameManager.checkShipAndStationEquipmentOrError()?.let {
                returnTheWorld(it)
                return@launch
            }

            if (!gameManager.checkAndUpdateStockEUS(stationItem)) {
                returnTheWorld(R.string.low_ship_eus)
                return@launch
            }

            updateUIShipInfo()
            updateCurrentLocation(stationItem.data)
            updateAdapterData(stationItem)
        }
    }

    private suspend fun updateAdapterData(selected: StationItemData? = null) {
        stationRepository.getCachedStationList()?.let { list ->
            val selectedPosition = list.indexOfFirst { it.name == selected?.name }
            _sliderAdapterLiveData.postValue(
                gameManager.getAdapterData(list, selectedPosition)
            )
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
            _favoriteAdapterLiveData.postValue(gameManager.getAdapterData(it))
        }
    }

    private suspend fun returnTheWorld(resId: Int) {
        timer.stop()
        updateCurrentLocation(stationRepository.getWorldStation())
        _finishGameLiveData.postValue(resourcesProvider.getString(resId))
    }

    fun startTimer() {
        timer.start()
    }

    override fun onCleared() {
        timer.stop()
        super.onCleared()
    }
}