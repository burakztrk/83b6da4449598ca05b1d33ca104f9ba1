package com.ozturkburak.outerworlds.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozturkburak.outerworlds.database.DBConstants


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
    val stock: Int?,
    @ColumnInfo
    val need: Int?
)