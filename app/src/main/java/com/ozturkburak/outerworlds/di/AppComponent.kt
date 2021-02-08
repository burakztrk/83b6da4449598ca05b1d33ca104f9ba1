package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.base.ResourcesProvider
import com.ozturkburak.outerworlds.features.shipcreator.ShipCreatorViewModel
import com.ozturkburak.outerworlds.features.stationlist.StationListViewModel
import com.ozturkburak.outerworlds.features.stationlist.station.GameManager
import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.ShipRepositoryImpl
import com.ozturkburak.outerworlds.repo.StationRepository
import com.ozturkburak.outerworlds.repo.StationRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single {
        ResourcesProvider(context = get())
    }

    single {
        GameManager(
            stationRepository = get(),
            shipRepository = get(),
            resourcesProvider = get()
        )
    }
}