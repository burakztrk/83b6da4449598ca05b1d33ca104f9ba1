package com.ozturkburak.outerworlds.application

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.ozturkburak.outerworlds.di.databaseModule
import com.ozturkburak.outerworlds.di.module
import com.ozturkburak.outerworlds.di.repositoryModule
import com.ozturkburak.outerworlds.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class OuterWorldsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@OuterWorldsApp)
            modules(
                listOf(
                    viewModelModule,
                    module,
                    databaseModule,
                    repositoryModule
                )
            )
        }

        Logger.addLogAdapter(AndroidLogAdapter())
    }
}