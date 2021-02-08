package com.ozturkburak.outerworlds.features.stationlist.station

import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.base.ResourcesProvider
import com.ozturkburak.outerworlds.base.orZero
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.database.entity.StationEntity
import com.ozturkburak.outerworlds.features.stationlist.station.list.AdapterDataMapper
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.ozturkburak.outerworlds.repo.Resource
import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.StationRepository

class GameManager(
    private val stationRepository: StationRepository,
    private val shipRepository: ShipRepository,
    private val resourcesProvider: ResourcesProvider
) {

    suspend fun checkAndUpdateStockEUS(stationItem: StationItemData): Boolean {
        val newShipData = shipRepository.getShipData()

        newShipData.apply {
            val checkShipStock = this.capacity - stationItem.data.need.orZero() < 0
            val checkShipEUS = this.speed - stationItem.eus?.toInt().orZero() < 0

            if (checkShipStock || checkShipEUS) {
                return false
            }

            this.capacity -= stationItem.data.need ?: 0
            stationRepository.updateStation(stationItem.data.apply {
                stock = capacity
                need = 0
            })

            stationItem.eus?.toInt()?.let {
                speed -= it
            }
        }
        shipRepository.saveShipData(newShipData)
        return true
    }

    suspend fun getStations(): Resource {
        val list = stationRepository.fetchStationList()

        return if (list.isNullOrEmpty()) {
            Resource.Error(resourcesProvider.getString(R.string.error_list_empty))
        } else {
            getAdapterData(list)
        }
    }

    suspend fun getAdapterData(
        list: List<StationEntity>,
        selectedStationPos: Int? = null
    ) = Resource.Success(
        data = AdapterDataMapper(
            stationList = list,
            stationRepository.getCurrentStation(),
            shipRepository.getShipData()
        ).execute(),
        selectedPosition = selectedStationPos
    )

    suspend fun getTimerMaxCounter() = shipRepository.getShipData().strength / 1000

    suspend fun checkShipAndStationEquipmentOrError(): Int? {
        return shipRepository.getShipData().run {
            when {
                health <= 0 -> R.string.low_health
                shipEUSisEmpty(this) -> R.string.fail_eus
                stationRepository.isStationsStockFull() -> R.string.success_stations_stock
                else -> null
            }
        }
    }

    private suspend fun shipEUSisEmpty(shipInfo: ShipEntity): Boolean {
        return shipInfo.speed < stationRepository.targetStationsTotalEUS()
    }
}