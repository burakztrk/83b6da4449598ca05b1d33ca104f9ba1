package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.data.repo.ShipRepository
import com.ozturkburak.outerworlds.data.repo.ShipRepositoryImpl
import com.ozturkburak.outerworlds.data.repo.StationRepository
import com.ozturkburak.outerworlds.data.repo.StationRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<ShipRepository> {
        ShipRepositoryImpl(shipDao = get())
    }

    single<StationRepository> {
        StationRepositoryImpl(stationApi = get(), stationDao = get())
    }
}