package com.ozturkburak.outerworlds.di

import com.ozturkburak.outerworlds.BuildConfig
import com.ozturkburak.outerworlds.api.StationApi
import com.ozturkburak.outerworlds.base.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun provideHttpClient() = OkHttpClient.Builder().apply {
        connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    fun provideRetrofit(client: OkHttpClient, baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    single { provideHttpClient() }
    single {
        provideRetrofit(client = get(), Constants.API_URL)
    }
}


val apiModule = module {
    fun provideStationApi(retrofit: Retrofit): StationApi {
        return retrofit.create(StationApi::class.java)
    }
    single { provideStationApi(retrofit = get()) }
}