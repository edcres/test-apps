package com.example.testfirestorev2.testhousematept2

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// https://medium.com/firebase-tips-tricks/how-to-use-kotlin-flows-with-firestore-6c7ee9ae12f3

class Housemate2ViewModel: ViewModel() {

    private val TAG = "RecyclerView mTAG"
    private val housemateRepository = HousemateRepository()

    private var _shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    val shoppingItems: LiveData<MutableList<ShoppingItem>> get() = _shoppingItems
    private var _choreItems = MutableLiveData<MutableList<ChoresItem>>()
    val choreItems: LiveData<MutableList<ChoresItem>> get() = _choreItems

    init {
        Log.d(TAG, "ViewModel initialized")
        setShoppingItems()
        setChoreItems()
    }

    // DATABASE FUNCTIONS //
    // set up shopping realtime fetching
    private fun setShoppingItems() {
        // get shopping items realtime using Flow, liveData and coroutines
        CoroutineScope(IO).launch {
            housemateRepository.setUpShoppingRealtimeFetching().collect {
                _shoppingItems.postValue(it)
            }
        }
    }

    // set up chore realtime fetching
    private fun setChoreItems() {
        CoroutineScope(IO).launch {
            housemateRepository.setUpChoresRealtimeFetching().collect {
                _choreItems.postValue(it)
            }
        }
    }

    fun sendShoppingItemToDatabase(
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateRepository.addShoppingItemToDb(
            itemName, itemQuantity, itemCost,
            purchaseLocation, itemNeededBy, itemPriority, addedBy
        )
    }
    fun sendChoresItemToDatabase(
        itemName: String,
        itemDifficulty: Int,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateRepository.addChoresItemToDb(
            itemName, itemDifficulty, itemNeededBy, itemPriority, addedBy
        )
    }

    fun sendShoppingVolunteerToDb(itemName: String, volunteerName: String) {
        housemateRepository.sendShoppingVolunteerToDb(itemName, volunteerName)
    }
    fun sendChoresVolunteerToDb(itemName: String, volunteerName: String) {
        housemateRepository.sendChoresVolunteerToDb(itemName, volunteerName)
    }
    fun deleteShoppingListItem(itemName: String) {
        housemateRepository.deleteShoppingListItem(itemName)
    }
    fun deleteChoresListItem(itemName: String) {
        housemateRepository.deleteChoresListItem(itemName)
    }
    // DATABASE FUNCTIONS //

    // HELPER FUNCTIONS //
    // HELPER FUNCTIONS //
}