package com.example.testfirestorev2.testhousematept2

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// https://medium.com/firebase-tips-tricks/how-to-use-kotlin-flows-with-firestore-6c7ee9ae12f3

class Housemate2ViewModel: ViewModel() {

    private val TAG = "HousematePt2mTAG"
    private val housemateRepository = HousemateRepository()
    lateinit var sharedPref: SharedPreferences
    val groupIdSPTag = "Group ID"
    val clientIdSPTag = "Client ID"
    var clientGroupIDCollection: String? = null
    var clientIDCollection: String? = null
    private var _shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    val shoppingItems: LiveData<MutableList<ShoppingItem>> get() = _shoppingItems
    private var _choreItems = MutableLiveData<MutableList<ChoresItem>>()
    val choreItems: LiveData<MutableList<ChoresItem>> get() = _choreItems

    init {
        //todo: put the initial db fetching functions here. Instead of the activity
        housemateRepository
        Log.d(TAG, "ViewModel initialized")
        // if i have different views, everytime i call the viewModel, the variables might be reset
        //  unless maybe it's only initialized the firs time it's called
        Log.d(TAG, "_shoppingItems: ${_shoppingItems.value?.size}")
        Log.d(TAG, "_shoppingItems: ${_choreItems.value?.size}")
    }

    // DATABASE FUNCTIONS //
    // set up shopping realtime fetching
    fun setShoppingItemsRealtime() {
        // get shopping items realtime using Flow, liveData and coroutines
        CoroutineScope(IO).launch {
//            housemateRepository.setUpShoppingRealtimeFetching(clientGroupIDCollection!!)
//                .collect {
//                    _shoppingItems.postValue(it)
//                }
        }
    }

    // set up chore realtime fetching
    fun setChoreItemsRealtime() {
        CoroutineScope(IO).launch {
//            housemateRepository.setUpChoresRealtimeFetching(clientGroupIDCollection
//            !!).collect {
//                _choreItems.postValue(it)
//            }
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
            clientGroupIDCollection!!, itemName, itemQuantity, itemCost,
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
            clientGroupIDCollection!!, itemName, itemDifficulty, itemNeededBy, itemPriority, addedBy
        )
    }

    fun sendShoppingVolunteerToDb(itemName: String, volunteerName: String) {
        housemateRepository.sendShoppingVolunteerToDb(
            clientGroupIDCollection!!,
            itemName,
            volunteerName
        )
    }
    fun sendChoresVolunteerToDb(itemName: String, volunteerName: String) {
        housemateRepository.sendChoresVolunteerToDb(
            clientGroupIDCollection!!,
            itemName,
            volunteerName
        )
    }
    fun deleteShoppingListItem(itemName: String) {
        housemateRepository.deleteShoppingListItem(clientGroupIDCollection!!, itemName)
    }
    fun deleteChoresListItem(itemName: String) {
        housemateRepository.deleteChoresListItem(clientGroupIDCollection!!, itemName)
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
        CoroutineScope(IO).launch {
            clientGroupIDCollection = housemateRepository.getLastGroupAdded()

            withContext(Main) {
                if (clientGroupIDCollection != null) {
                    sendDataToSP(
                        groupIdSPTag,
                        clientGroupIDCollection!!
                    )
                    setClientID()
                } else {
                    Log.e(TAG, "generateClientGroupID(): clientGroupIDCollection is null")
                }
            }
        }
    }

    private fun generateClientID() {
        housemateRepository.getLastClientAdded(clientGroupIDCollection!!)
    }

    private fun setClientID() {
        clientIDCollection = getDataFromSP(clientIdSPTag)
        if (clientIDCollection == null) {
            generateClientID()
        }
    }

    fun getCurrentGroupID(): String? {
        clientGroupIDCollection = getDataFromSP(groupIdSPTag)
        return clientGroupIDCollection
    }
    // HELPER FUNCTIONS //
}
