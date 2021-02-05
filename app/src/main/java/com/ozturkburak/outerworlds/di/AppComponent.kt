package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.MainActivityViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel{
        MainActivityViewModel()
    }
}