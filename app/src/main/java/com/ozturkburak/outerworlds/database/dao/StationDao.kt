package com.ozturkburak.outerworlds.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ozturkburak.outerworlds.database.DBConstants
import com.ozturkburak.outerworlds.database.entity.StationEntity

@Dao
interface StationDao {
    @Query("SELECT * FROM ${DBConstants.DB_TABLE_STATION}")
    fun getList(): List<StationEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stationList: List<StationEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(stationEntity: StationEntity)

    @Delete
    suspend fun delete(stationEntity: StationEntity)

    @Query("SELECT * FROM ${DBConstants.DB_TABLE_STATION} WHERE name=:name")
    suspend fun get(name: String): StationEntity?

    @Query("SELECT * FROM ${DBConstants.DB_TABLE_STATION} WHERE current_station=1")
    suspend fun getCurrentStation(): StationEntity?

}