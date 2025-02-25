package com.ozturkburak.outerworlds.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozturkburak.outerworlds.data.local.DBConstants


@Entity(tableName = DBConstants.DB_TABLE_STATION)
data class StationEntity(
    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "coordinate_x")
    val coordinateX: Double?,

    @ColumnInfo(name = "coordinate_y")
    val coordinateY: Double?,

    @ColumnInfo
    val capacity: Int?,

    @ColumnInfo
    var stock: Int?,

    @ColumnInfo
    var need: Int?,

    @ColumnInfo(name = "current_station")
    var currentStation: Boolean = false,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)