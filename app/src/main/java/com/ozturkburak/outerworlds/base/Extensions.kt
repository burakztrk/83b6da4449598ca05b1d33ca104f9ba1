package com.ozturkburak.outerworlds.base

import android.animation.Animator
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.ozturkburak.outerworlds.database.entity.StationEntity
import com.ozturkburak.outerworlds.features.stationlist.station.list.SliderAdapter
import com.yarolegovich.discretescrollview.DiscreteScrollView
import kotlin.math.pow
import kotlin.math.sqrt

class Task<T>(val executable: (value: T) -> Unit)

fun LottieAnimationView.onAnimationEnd(task: () -> Unit) {
    addAnimatorListener(
        object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                task.invoke()
            }
        }
    )
}

fun <T> LifecycleOwner.observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}

fun Double?.orZero() = this ?: 0.0

fun StationEntity.calculateDistance(target: StationEntity) = getDistance(this, target)

private fun getDistance(
    currentStation: StationEntity,
    targetStation: StationEntity,
) = sqrt(
    (currentStation.coordinateX.orZero() - targetStation.coordinateX.orZero()).pow(2) +
            (currentStation.coordinateY.orZero() - targetStation.coordinateY.orZero()).pow(2)
)


fun DiscreteScrollView.onScrollStartListener(listener: Task<Unit>) {
    addScrollStateChangeListener(object :
        DiscreteScrollView.ScrollStateChangeListener<SliderAdapter.ViewHolder> {
        override fun onScrollStart(
            currentItemHolder: SliderAdapter.ViewHolder,
            adapterPosition: Int
        ) {
            listener.executable.invoke(Unit)
        }

        override fun onScrollEnd(
            currentItemHolder: SliderAdapter.ViewHolder,
            adapterPosition: Int
        ) {
        }

        override fun onScroll(
            scrollPosition: Float,
            currentPosition: Int,
            newPosition: Int,
            currentHolder: SliderAdapter.ViewHolder?,
            newCurrent: SliderAdapter.ViewHolder?
        ) {
        }
    })
}
