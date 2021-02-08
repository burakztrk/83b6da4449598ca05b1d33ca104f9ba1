package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.utils.ResourcesProvider
import com.ozturkburak.outerworlds.utils.usecase.GameManager
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