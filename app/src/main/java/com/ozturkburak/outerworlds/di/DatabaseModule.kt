package com.ozturkburak.outerworlds.di

import android.app.Application
import androidx.room.Room
import com.ozturkburak.outerworlds.data.local.DBConstants
import com.ozturkburak.outerworlds.data.local.GameDatabase
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, GameDatabase::class.java, DBConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    fun provideShipDao(database: GameDatabase) = database.shipDao()

    fun provideStationDao(database: GameDatabase) = database.stationDao()

    single { provideDatabase(get()) }
    single { provideShipDao(database = get()) }
    single { provideStationDao(database = get()) }
}