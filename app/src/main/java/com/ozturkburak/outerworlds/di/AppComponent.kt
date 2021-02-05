package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.features.splash.SplashActivityViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel{
        SplashActivityViewModel()
    }
}