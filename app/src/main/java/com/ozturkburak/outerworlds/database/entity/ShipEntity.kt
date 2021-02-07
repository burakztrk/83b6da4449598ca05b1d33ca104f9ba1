package com.ozturkburak.outerworlds.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozturkburak.outerworlds.database.DBConstants

@Entity(tableName = DBConstants.DB_TABLE_SHIP)
data class ShipEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "strength")
    val strength: Int,
    @ColumnInfo(name = "speed")
    var speed: Int,
    @ColumnInfo(name = "capacity")
    var capacity: Int
)