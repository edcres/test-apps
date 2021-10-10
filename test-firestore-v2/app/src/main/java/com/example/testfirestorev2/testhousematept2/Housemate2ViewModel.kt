package com.example.testfirestorev2.testhousematept2

import android.annotation.SuppressLint
import android.content.SharedPreferences
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

    val groupIdSPTag = "Group ID"
    val clientIdSPTag = "Client ID"

    lateinit var sharedPref: SharedPreferences
    var clientGroupIDCollection: String? = null
    var clientIDCollection: String? = null
    // todo: send client ID to a list of users to the database

    private var _shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    val shoppingItems: LiveData<MutableList<ShoppingItem>> get() = _shoppingItems
    private var _choreItems = MutableLiveData<MutableList<ChoresItem>>()
    val choreItems: LiveData<MutableList<ChoresItem>> get() = _choreItems

    init {
        Log.d(TAG, "ViewModel initialized")
    }

    // DATABASE FUNCTIONS //
    // set up shopping realtime fetching
    fun setShoppingItemsRealtime() {
        // get shopping items realtime using Flow, liveData and coroutines
        CoroutineScope(IO).launch {
            housemateRepository.setUpShoppingRealtimeFetching().collect {
                _shoppingItems.postValue(it)
            }
        }
    }

    // set up chore realtime fetching
    fun setChoreItemsRealtime() {
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

    // SHARED PREFERENCE //
    fun getDataFromSP(theTag: String): String? {
        return sharedPref.getString(theTag, null)
    }
    @SuppressLint("ApplySharedPref")
    fun sendDataToSP(theTag: String, dataToSend: String) {
        val spEditor: SharedPreferences.Editor = sharedPref.edit()
        spEditor.putString(theTag, dataToSend).commit()
    }
    @SuppressLint("ApplySharedPref")
    fun clearSP() {
        sharedPref.edit().clear().commit()
    }
    // SHARED PREFERENCE //

    // HELPER FUNCTIONS //
    fun generateClientGroupID() {
        // Get the latest groupID from the remote db (ie. 00000001asdfg)
        housemateRepository.getLastGroupAdded()
    }

    fun generateClientID() {
        housemateRepository.getLastClientAdded()
    }

    fun setClientID() {
        clientIDCollection = getDataFromSP(clientIdSPTag)
        if(clientIDCollection == null) {
            if(clientGroupIDCollection != null) {
                generateClientID()
            }
        }
    }
    fun getCurrentGroupID(): String? {
        clientGroupIDCollection = getDataFromSP(groupIdSPTag)
        return clientGroupIDCollection
    }
    // HELPER FUNCTIONS //
}