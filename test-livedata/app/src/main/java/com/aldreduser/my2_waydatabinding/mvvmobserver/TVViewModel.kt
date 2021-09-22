package com.aldreduser.my2_waydatabinding.mvvmobserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TVViewModel(): ViewModel() {
    var number = 1
    private val _currentNumber: MutableLiveData<Int> = MutableLiveData<Int>()
    val currentNumber: LiveData<Int> get() = _currentNumber
    private val _currentBoolean: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val currentBoolean: LiveData<Boolean> get() = _currentBoolean

    fun incrementText() {
        _currentNumber.value = ++number
        _currentBoolean.value = number % 2 == 0  // if it's even, set to true
    }
}