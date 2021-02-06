package com.ozturkburak.outerworlds.di

import android.app.Application
import androidx.room.Room
import com.ozturkburak.outerworlds.base.ResourcesProvider
import com.ozturkburak.outerworlds.database.DBConstants
import com.ozturkburak.outerworlds.database.GameDatabase
import com.ozturkburak.outerworlds.database.dao.ShipDao
import com.ozturkburak.outerworlds.features.shipcreator.ShipCreatorViewModel
import com.ozturkburak.outerworlds.features.splash.SplashActivityViewModel
import com.ozturkburak.outerworlds.features.stationlist.StationListViewModel
import com.ozturkburak.outerworlds.repo.ShipRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SplashActivityViewModel()
    }
    viewModel {
        ShipCreatorViewModel(resources = get(), shipRepo = get())
    }
    viewModel {
        StationListViewModel()
    }
}

val module = module {
    single { ResourcesProvider(context = get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, GameDatabase::class.java, DBConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    fun provideShipDao(database: GameDatabase) = database.shipDao()

    single { provideDatabase(get()) }
    single { provideShipDao(database = get()) }
}

val repositoryModule = module {

    fun provideShipRepository(dao: ShipDao) = ShipRepositoryImpl(shipDao = dao)

    single { provideShipRepository(dao = get()) }
}
