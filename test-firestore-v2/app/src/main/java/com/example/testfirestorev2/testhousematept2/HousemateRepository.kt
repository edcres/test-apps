package com.example.testfirestorev2.testhousematept2

import kotlinx.coroutines.flow.Flow

class HousemateRepository {

    private val housemateAPIService = HousemateAPIService()

    fun setUpShoppingRealtimeFetching(): Flow<MutableList<ShoppingItem>> {
        return housemateAPIService.getShoppingItemsRealtime()
    }

    fun setUpChoresRealtimeFetching(): Flow<MutableList<ChoresItem>> {
        return housemateAPIService.getChoreItemsRealtime()
    }

    // add item
    fun addShoppingItemToDb(
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateAPIService.addShoppingItemToDatabase(
            itemName, itemQuantity, itemCost,
            purchaseLocation, itemNeededBy, itemPriority, addedBy
        )
    }
    fun addChoresItemToDb(
        itemName: String,
        itemDifficulty: Int,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateAPIService.addChoresItemToDatabase(
            itemName, itemDifficulty, itemNeededBy, itemPriority, addedBy
        )
    }

    // send volunteer name to db
    fun sendShoppingVolunteerToDb(itemName: String, volunteerName: String) {
        housemateAPIService.sendVolunteerToDb(ShoppingItem::class, itemName, volunteerName)
    }

    fun sendChoresVolunteerToDb(itemName: String, volunteerName: String) {
        housemateAPIService.sendVolunteerToDb(ChoresItem::class, itemName, volunteerName)
    }

    // delete item
    fun deleteShoppingListItem(itemName: String) {
        housemateAPIService.deleteListItem(ShoppingItem::class, itemName)
    }

    fun deleteChoresListItem(itemName: String) {
        housemateAPIService.deleteListItem(ChoresItem::class, itemName)
    }
}