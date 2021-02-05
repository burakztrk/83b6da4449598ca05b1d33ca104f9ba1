package com.ozturkburak.outerworlds.features.splash

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger

class SplashActivityViewModel : ViewModel() {
    init {
        Logger.d("ViewModel init")
    }


    override fun onCleared() {
        super.onCleared()
        Logger.d("ViewModel onClear")
    }

    fun foo(){
        Logger.d("foo")
    }

}