package com.example.testfirestorev2.testhousematept2

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// https://medium.com/firebase-tips-tricks/how-to-use-kotlin-flows-with-firestore-6c7ee9ae12f3

class Housemate2ViewModel: ViewModel() {

    private val TAG = "RecyclerView mTAG"

    private val housemateRepository = HousemateRepository()

    private var _shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    val shoppingItems: LiveData<MutableList<ShoppingItem>> get() = _shoppingItems

    init {
        Log.d(TAG, "ViewModel initialized")

        setShoppingItems()
    }

    // HELPER FUNCTIONS //
    private fun setShoppingItems() {
        // get shopping items realtime using Flow, liveData and coroutines
        CoroutineScope(IO).launch {

            housemateRepository.setUpShoppingRealtimeFetching().collect {
//                _shoppingItems.value = it
                _shoppingItems.postValue(it)

            }
        }
    }
    // HELPER FUNCTIONS //
}