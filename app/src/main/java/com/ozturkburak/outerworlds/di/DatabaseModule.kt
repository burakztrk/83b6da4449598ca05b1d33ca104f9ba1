package com.ozturkburak.outerworlds.di

import android.app.Application
import androidx.room.Room
import com.ozturkburak.outerworlds.database.DBConstants
import com.ozturkburak.outerworlds.database.GameDatabase
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, GameDatabase::class.java, DBConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    fun provideShipDao(database: GameDatabase) = database.shipDao()

    single { provideDatabase(get()) }
    single { provideShipDao(database = get()) }
}