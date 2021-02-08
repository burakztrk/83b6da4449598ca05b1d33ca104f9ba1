package com.ozturkburak.outerworlds.features.stationlist.station.list

import com.ozturkburak.outerworlds.base.BaseUseCase
import com.ozturkburak.outerworlds.base.Constants
import com.ozturkburak.outerworlds.base.calculateDistance
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.database.entity.StationEntity

class AdapterDataMapper(
    private val stationList: List<StationEntity>,
    private val currentStation: StationEntity,
    private val shipInfo: ShipEntity
) : BaseUseCase<List<StationItemData>> {

    override fun execute(): List<StationItemData> {
        return stationList.map {
            convertItemData(it)
        }
    }

    private fun convertItemData(targetStation: StationEntity): StationItemData {
        val eus = calculateEUS(currentStation, targetStation, shipInfo)
        return StationItemData(
            data = targetStation,
            name = targetStation.name,
            stockText = "${targetStation.capacity} / ${targetStation.stock}",
            eus = eus,
            eusText = "${eus.toInt()} EUS",
            missionSuccess = if (targetStation.name == Constants.STATION_WORLD_ID) false
            else targetStation.capacity == targetStation.stock
        )
    }

    // FIXME: 2/7/21 Hesaplamada hata var terkrar kontron edilmeli
    private fun calculateEUS(
        currentStation: StationEntity,
        targetStation: StationEntity,
        shipInfo: ShipEntity
    ) = currentStation.calculateDistance(targetStation) / shipInfo.speed
}