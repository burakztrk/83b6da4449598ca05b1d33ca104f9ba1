package com.ozturkburak.outerworlds.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozturkburak.outerworlds.database.dao.ShipDao
import com.ozturkburak.outerworlds.database.entity.ShipEntity

@Database(entities = [ShipEntity::class], version = DBConstants.DB_VERSION)
abstract class GameDatabase: RoomDatabase() {
    abstract fun shipDao() : ShipDao
}