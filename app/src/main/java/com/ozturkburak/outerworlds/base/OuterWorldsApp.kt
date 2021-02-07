package com.ozturkburak.outerworlds.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.ozturkburak.outerworlds.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class OuterWorldsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    private fun initDi() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@OuterWorldsApp)
            modules(
                listOf(
                    viewModelModule,
                    module,
                    databaseModule,
                    repositoryModule,
                    networkModule,
                    apiModule
                )
            )
        }
    }
}