package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.repo.ShipRepository
import com.ozturkburak.outerworlds.repo.ShipRepositoryImpl
import com.ozturkburak.outerworlds.repo.StationRepository
import com.ozturkburak.outerworlds.repo.StationRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<ShipRepository> {
        ShipRepositoryImpl(shipDao = get())
    }

    single<StationRepository> {
        StationRepositoryImpl(stationApi = get(), stationDao = get())
    }
}