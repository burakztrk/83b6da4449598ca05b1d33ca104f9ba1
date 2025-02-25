package com.ozturkburak.outerworlds.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozturkburak.outerworlds.data.local.DBConstants

@Entity(tableName = DBConstants.DB_TABLE_SHIP)
data class ShipEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "strength")
    var strength: Int,
    @ColumnInfo(name = "speed")
    var speed: Int,
    @ColumnInfo(name = "capacity")
    var capacity: Int,
    @ColumnInfo(name = "health")
    var health: Int = 100
)