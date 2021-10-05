package com.example.testfirestorev2.testhousematept2

import kotlinx.coroutines.flow.Flow

class HousemateRepository {

    private val housemateAPIService = HousemateAPIService()

    fun setUpShoppingRealtimeFetching(): Flow<MutableList<ShoppingItem>> {
        return housemateAPIService.getShoppingItemsRealtime()
    }

    // add item
    fun addItemToDb(
        name: String,
        neededBy: String,
        priority: String
    ) {
        housemateAPIService.addItemToDatabase(name, neededBy, priority)
    }

    // remove item
}