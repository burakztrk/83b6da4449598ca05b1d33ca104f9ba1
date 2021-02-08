package com.ozturkburak.outerworlds.features.shipcreator

import androidx.annotation.StringRes
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.base.Constants.Companion.CAPACITY_FACTOR
import com.ozturkburak.outerworlds.base.Constants.Companion.SPEED_FACTOR
import com.ozturkburak.outerworlds.base.Constants.Companion.STRENGTH_FACTOR
import com.ozturkburak.outerworlds.base.ResourcesProvider
import com.ozturkburak.outerworlds.database.entity.ShipEntity
import com.ozturkburak.outerworlds.repo.ShipRepository
import kotlinx.coroutines.launch

class ShipCreatorViewModel(
    private val resources: ResourcesProvider,
    private val shipRepo: ShipRepository
) : ViewModel() {

    companion object {
        private const val TOTAL_POINTS = 15
    }

    val inputName = ObservableField<String>()
    val totalPointsObservable = ObservableField<String>()
    var inputStrength: Int = 0
    var inputSpeed: Int = 0
    var inputCapacity: Int = 0

    private val _capacityLiveData = MutableLiveData<Int>()
    val capacityLiveData: LiveData<Int> get() = _capacityLiveData

    private val _speedLiveData = MutableLiveData<Int>()
    val speedLiveData: LiveData<Int> get() = _speedLiveData

    private val _strengthLiveData = MutableLiveData<Int>()
    val strengthLiveData: MutableLiveData<Int> get() = _strengthLiveData

    private val _showErrorLiveData = MutableLiveData<String>()
    val showErrorLiveData: LiveData<String> get() = _showErrorLiveData

    private val _startStationListLiveData = MutableLiveData<Unit>()
    val startStationListLiveData: MutableLiveData<Unit> get() = _startStationListLiveData

    init {
        updateTotalPoints()
    }

    fun onStrengthChanged(newValue: Int) {
        inputStrength = newValue
        if (getUsedPoints() > TOTAL_POINTS) {
            val gap = getUsedPoints() - TOTAL_POINTS
            inputStrength -= gap
            strengthLiveData.value = inputStrength
        }
        updateTotalPoints()
    }

    fun onSpeedChanged(newValue: Int) {
        inputSpeed = newValue
        if (getUsedPoints() > TOTAL_POINTS) {
            val gap = getUsedPoints() - TOTAL_POINTS
            inputSpeed -= gap
            _speedLiveData.value = inputSpeed
        }
        updateTotalPoints()
    }

    fun onCapacityChanged(newValue: Int) {
        inputCapacity = newValue
        if (getUsedPoints() > TOTAL_POINTS) {
            val gap = getUsedPoints() - TOTAL_POINTS
            inputCapacity -= gap
            _capacityLiveData.value = inputCapacity
        }
        updateTotalPoints()
    }

    private fun getUsedPoints() = inputStrength + inputSpeed + inputCapacity

    private fun updateTotalPoints() {
        totalPointsObservable.set((TOTAL_POINTS - getUsedPoints()).toString())
    }

    fun onContinueClick() {
        when {
            inputName.get().isNullOrEmpty() -> showError(R.string.error_name_empty)
            inputStrength == 0 || inputSpeed == 0 || inputCapacity == 0 -> showError(R.string.error_points)
            getUsedPoints() != 15 -> showError(R.string.error_points)
            else -> saveAndContinue()
        }
    }

    private fun showError(@StringRes resId: Int) {
        _showErrorLiveData.value = resources.getString(resId)
    }

    private fun saveAndContinue() {
        inputName.get()?.let {
            val shipData = ShipEntity(
                name = it,
                strength = inputStrength * STRENGTH_FACTOR,
                speed = inputSpeed * SPEED_FACTOR,
                capacity = inputCapacity * CAPACITY_FACTOR
            )
            viewModelScope.launch {
                shipRepo.saveShipData(shipData)
                _startStationListLiveData.postValue(Unit)
            }
        }
    }
}