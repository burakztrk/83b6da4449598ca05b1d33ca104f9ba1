package com.ozturkburak.outerworlds.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ozturkburak.outerworlds.databinding.ActivitySplashBinding
import com.ozturkburak.outerworlds.ui.shipcreator.ShipCreatorActivity
import com.ozturkburak.outerworlds.utils.onAnimationEnd

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val TEXT_FADE_IN_DURATION = 1000L
        private const val TEXT_ANIM_AFTER_DELAY = 1000L
    }

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.animationView.onAnimationEnd {
            fadeInTitle()
        }
    }

    private fun fadeInTitle() {
        binding.title.run {
            animate().withEndAction {
                postDelayed({ startShipCreatorActivity() }, TEXT_ANIM_AFTER_DELAY)
            }.setDuration(TEXT_FADE_IN_DURATION)
                .alpha(1f)
        }
    }

    private fun startShipCreatorActivity() {
        startActivity(Intent(this@SplashActivity, ShipCreatorActivity::class.java))
        finish()
    }
}