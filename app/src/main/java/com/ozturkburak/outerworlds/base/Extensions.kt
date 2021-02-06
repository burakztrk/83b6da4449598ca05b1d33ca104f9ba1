package com.ozturkburak.outerworlds.base

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView

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

fun <T> AppCompatActivity.observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}