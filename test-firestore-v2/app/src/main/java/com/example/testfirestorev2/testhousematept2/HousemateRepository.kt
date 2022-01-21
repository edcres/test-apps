package com.example.testfirestorev2.testhousematept2

import android.util.Log
import kotlinx.coroutines.flow.Flow

// todo: there's 2 main problems:
//      -one time db fetching
//          - Fetch the data (APIService)
//          - Return the value to the Repo (APIService)
//          - Listen for Repo data changes (ViewModel)
//      -realtime DB fetching
//          - Set up listening for data changes (APIService)
//          - Somehow update the data changes in the repo
//          - Somehow update the data changes in the viewModel

class HousemateRepository {

    private val housemateAPIService = HousemateAPIService()

    // todo: uncomment this
//    fun setUpShoppingRealtimeFetching(groupID: String): Flow<MutableList<ShoppingItem>> {
//        return housemateAPIService.getShoppingItemsRealtime(groupID)
//    }

    // todo: uncomment this
//    fun setUpChoresRealtimeFetching(groupID: String): Flow<MutableList<ChoresItem>> {
//        return housemateAPIService.getChoreItemsRealtime(groupID)
//    }

    // add item
    fun addShoppingItemToDb(
        groupID: String,
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateAPIService.addShoppingItemToDatabase(
            groupID, itemName, itemQuantity, itemCost,
            purchaseLocation, itemNeededBy, itemPriority, addedBy
        )
    }
    fun addChoresItemToDb(
        groupID: String,
        itemName: String,
        itemDifficulty: Int,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateAPIService.addChoresItemToDatabase(
            groupID, itemName, itemDifficulty, itemNeededBy, itemPriority, addedBy
        )
    }

    // get the last group added String
    suspend fun getLastGroupAdded(): String? {
        return housemateAPIService.getLastGroupAdded()
    }

    // get the latest clientID from the db
    fun getLastClientAdded(groupID: String) {
        housemateAPIService.getLastClientAdded(groupID)
    }

    // send volunteer name to db
    fun sendShoppingVolunteerToDb(groupID: String, itemName: String, volunteerName: String) {
        housemateAPIService.sendVolunteerToDb(groupID, ShoppingItem::class, itemName, volunteerName)
    }

    fun sendChoresVolunteerToDb(groupID: String, itemName: String, volunteerName: String) {
        housemateAPIService.sendVolunteerToDb(groupID, ChoresItem::class, itemName, volunteerName)
    }

    // delete item
    fun deleteShoppingListItem(groupID: String, itemName: String) {
        housemateAPIService.deleteListItem(groupID, ShoppingItem::class, itemName)
    }

    fun deleteChoresListItem(groupID: String, itemName: String) {
        housemateAPIService.deleteListItem(groupID, ChoresItem::class, itemName)
    }
}