package com.ozturkburak.outerworlds.features.shipcreator

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShipCreatorViewModel : ViewModel() {

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
            inputName.get().isNullOrEmpty() -> showError("İsim boş geçilemez!")
            inputStrength == 0 || inputSpeed == 0 || inputCapacity == 0 -> showError("Yetenek boş olamaz!")
            getUsedPoints() != 15 -> showError("Tüm puanları dağıtmalısın!")
            else -> saveAndContinue()
        }
    }

    private fun showError(message: String) {
        _showErrorLiveData.value = message
    }

    private fun saveAndContinue() {
// FIXME: 2/6/21 rooma kayit atilacak
    }

}