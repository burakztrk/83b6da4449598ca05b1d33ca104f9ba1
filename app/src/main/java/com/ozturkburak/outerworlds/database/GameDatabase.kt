package com.ozturkburak.outerworlds.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozturkburak.outerworlds.database.dao.ShipDao
import com.ozturkburak.outerworlds.database.dao.StationDao
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.database.entity.StationEntity

@Database(entities = [ShipEntity::class, StationEntity::class], version = DBConstants.DB_VERSION)
abstract class GameDatabase : RoomDatabase() {
    abstract fun shipDao(): ShipDao

    abstract fun stationDao(): StationDao
}