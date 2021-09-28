package com.example.testfirestorev2.testhousematept2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class Housemate2ViewModel: ViewModel() {
    private val _shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    val shoppingItems: LiveData<MutableList<ShoppingItem>> get() = _shoppingItems

    init {
        CoroutineScope(IO).launch {
            _shoppingItems.value
        }
    }
}