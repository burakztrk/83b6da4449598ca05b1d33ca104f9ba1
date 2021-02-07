package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.base.ResourcesProvider
import com.ozturkburak.outerworlds.features.shipcreator.ShipCreatorViewModel
import com.ozturkburak.outerworlds.features.splash.SplashActivityViewModel
import com.ozturkburak.outerworlds.features.stationlist.StationListViewModel
import com.ozturkburak.outerworlds.features.stationlist.favorites.FavoriteViewModel
import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.ShipRepositoryImpl
import com.ozturkburak.outerworlds.repo.StationRepository
import com.ozturkburak.outerworlds.repo.StationRepositoryImpl
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
        StationListViewModel(stationRepository = get(), shipRepository = get())
    }

    viewModel {
        FavoriteViewModel()
    }
}

val module = module {

    single {
        ResourcesProvider(context = get())
    }
}

val repositoryModule = module {

    single<ShipRepository> {
        ShipRepositoryImpl(shipDao = get())
    }

    single<StationRepository> {
        StationRepositoryImpl(stationApi = get() , stationDao = get())
    }
}
