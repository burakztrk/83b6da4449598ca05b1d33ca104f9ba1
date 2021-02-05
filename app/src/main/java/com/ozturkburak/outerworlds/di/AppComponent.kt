package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.features.shipcreator.ShipCreatorViewModel
import com.ozturkburak.outerworlds.features.splash.SplashActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SplashActivityViewModel()
        ShipCreatorViewModel()
    }
}