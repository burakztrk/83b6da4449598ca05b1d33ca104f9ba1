package com.ozturkburak.outerworlds.features.shipcreator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozturkburak.outerworlds.databinding.ActivityShipCreatorBinding

class ShipCreatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShipCreatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}