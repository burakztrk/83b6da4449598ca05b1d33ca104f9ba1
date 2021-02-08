package com.ozturkburak.outerworlds.data.local

import androidx.room.*
import com.ozturkburak.outerworlds.data.entity.ShipEntity

@Dao
interface ShipDao {

    @Query("SELECT * FROM ${DBConstants.DB_TABLE_SHIP}")
    suspend fun getList(): List<ShipEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shipEntity: ShipEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(shipEntity: ShipEntity)

    @Delete
    suspend fun delete(shipEntity: ShipEntity)
}