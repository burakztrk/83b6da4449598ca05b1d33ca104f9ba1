package com.ozturkburak.outerworlds.data.repo

import com.ozturkburak.outerworlds.data.local.ShipDao
import com.ozturkburak.outerworlds.data.entity.ShipEntity

interface ShipRepository {
    suspend fun getShipData(): ShipEntity

    suspend fun saveShipData(spaceShip: ShipEntity)
}

class ShipRepositoryImpl(private val shipDao: ShipDao) : ShipRepository {
    override suspend fun getShipData() = shipDao.getList().first()

    override suspend fun saveShipData(spaceShip: ShipEntity) {
        shipDao.getList().forEach {
            shipDao.delete(it)
        }
        shipDao.insert(spaceShip)
    }
}