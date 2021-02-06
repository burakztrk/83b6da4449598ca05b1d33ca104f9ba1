package com.ozturkburak.outerworlds.repo

import com.ozturkburak.outerworlds.database.dao.ShipDao
import com.ozturkburak.outerworlds.database.entity.ShipEntity

interface ShipRepository {
    suspend fun getShipData(): ShipEntity?

    suspend fun saveShipData(spaceShip: ShipEntity)
}

class ShipRepositoryImpl(private val shipDao: ShipDao) : ShipRepository {
    override suspend fun getShipData() = shipDao.getList().firstOrNull()

    override suspend fun saveShipData(spaceShip: ShipEntity) {
        shipDao.getList().forEach {
            shipDao.delete(it)
        }
        shipDao.insert(spaceShip)
    }
}