package com.ozturkburak.outerworlds.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozturkburak.outerworlds.data.entity.ShipEntity
import com.ozturkburak.outerworlds.data.entity.StationEntity

@Database(entities = [ShipEntity::class, StationEntity::class], version = DBConstants.DB_VERSION)
abstract class GameDatabase : RoomDatabase() {
    abstract fun shipDao(): ShipDao

    abstract fun stationDao(): StationDao
}