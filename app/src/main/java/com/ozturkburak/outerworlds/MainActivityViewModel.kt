package com.ozturkburak.outerworlds

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger

class MainActivityViewModel : ViewModel() {
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