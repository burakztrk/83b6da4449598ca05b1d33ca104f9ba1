package com.ozturkburak.outerworlds.utils

import androidx.lifecycle.viewModelScope
import com.ozturkburak.outerworlds.utils.Constants
import com.ozturkburak.outerworlds.utils.Task
import com.ozturkburak.outerworlds.ui.stationlist.StationListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Timer(
    private val viewModel: StationListViewModel,
) {

    private var scope: Job? = null
    private var counter: Int = 0
    private var timerIsActive: Boolean = true

    var onTick: Task<Int>? = null
    var onEndTime: Task<Unit>? = null
    var maxCounter: Int = 0

    fun start() {
        scope?.cancel()
        timerIsActive = true
        scope = viewModel.viewModelScope.launch {
            counter = maxCounter

            while (timerIsActive && counter > 0) {
                onTick?.executable?.invoke(counter--)
                delay(Constants.SECOND)

                if (counter <= 0) {
                    onEndTime?.executable?.invoke(Unit)
                    counter = maxCounter
                }
            }
        }
    }

    fun stop() {
        timerIsActive = false
        scope?.cancel()
    }
}