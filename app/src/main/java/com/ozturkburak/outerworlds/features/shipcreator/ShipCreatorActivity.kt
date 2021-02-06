package com.ozturkburak.outerworlds.features.shipcreator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ozturkburak.outerworlds.base.observeLiveData
import com.ozturkburak.outerworlds.databinding.ActivityShipCreatorBinding
import org.koin.android.viewmodel.ext.android.viewModel


class ShipCreatorActivity : AppCompatActivity() {

    private val viewModel: ShipCreatorViewModel by viewModel()

    private lateinit var binding: ActivityShipCreatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityShipCreatorBinding.inflate(layoutInflater).run {
            binding = this
            lifecycleOwner = this@ShipCreatorActivity
            viewModel = this@ShipCreatorActivity.viewModel
            setContentView(root)
        }

        initUI()
        observeViewModel()
    }

    private fun initUI() {
        binding.seekbarCapacity.addOnChangeListener { _, value, fromUser ->
            if (fromUser)
                viewModel.onCapacityChanged(value.toInt())
        }

        binding.seekbarSpeed.addOnChangeListener { _, value, fromUser ->
            if (fromUser)
                viewModel.onSpeedChanged(value.toInt())
        }

        binding.seekbarStrength.addOnChangeListener { _, value, fromUser ->
            if (fromUser)
                viewModel.onStrengthChanged(value.toInt())
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            observeLiveData(capacityLiveData) {
                binding.seekbarCapacity.value = it.toFloat()
            }

            observeLiveData(strengthLiveData) {
                binding.seekbarStrength.value = it.toFloat()
            }

            observeLiveData(speedLiveData) {
                binding.seekbarSpeed.value = it.toFloat()

            }

            observeLiveData(viewModel.showErrorLiveData) {
                showError(it)
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_SHORT).show()
    }
}
