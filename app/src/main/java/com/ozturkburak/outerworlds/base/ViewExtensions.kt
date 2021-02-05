package com.ozturkburak.outerworlds.base

import android.animation.Animator
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