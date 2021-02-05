package com.ozturkburak.outerworlds.features.shipcreator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozturkburak.outerworlds.databinding.ActivityShipCreatorBinding
import com.ozturkburak.outerworlds.features.splash.SplashActivityViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ShipCreatorActivity : AppCompatActivity() {

    private val viewModel: ShipCreatorViewModel by viewModel()

    private lateinit var binding: ActivityShipCreatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.foo()
    }
}