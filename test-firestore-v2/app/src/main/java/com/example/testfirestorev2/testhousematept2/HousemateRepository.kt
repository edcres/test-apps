package com.example.testfirestorev2.testhousematept2

import kotlinx.coroutines.flow.Flow

class HousemateRepository {

    private val housemateAPIService = HousemateAPIService()

    fun setUpShoppingRealtimeFetching(): Flow<MutableList<ShoppingItem>> {
        return housemateAPIService.getShoppingItemsRealtime()
    }

    // add item
    fun addItemToDb(
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        housemateAPIService.addItemToDatabase(
            itemName, itemQuantity, itemCost,
            purchaseLocation, itemNeededBy, itemPriority, addedBy
        )
    }

    // send volunteer name to db
    fun sendVolunteerToDb(itemName: String, volunteerName: String) {
        housemateAPIService.sendVolunteerToDb(itemName, volunteerName)
    }

        // remove item
}