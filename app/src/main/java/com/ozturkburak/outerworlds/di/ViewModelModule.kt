package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.ui.shipcreator.ShipCreatorViewModel
import com.ozturkburak.outerworlds.ui.stationlist.StationListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ShipCreatorViewModel(resources = get(), shipRepo = get())
    }
    viewModel {
        StationListViewModel(
            stationRepository = get(),
            shipRepository = get(),
            resourcesProvider = get(),
            gameManager = get()
        )
    }
}