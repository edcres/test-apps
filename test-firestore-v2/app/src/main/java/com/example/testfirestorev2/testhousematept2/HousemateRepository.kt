package com.example.testfirestorev2.testhousematept2

import kotlinx.coroutines.flow.Flow

class HousemateRepository {

    private val housemateAPIService = HousemateAPIService()

    // using flow
    fun setUpShoppingRealtimeFetching(): Flow<MutableList<ShoppingItem>> {
        return housemateAPIService.getShoppingItemsRealtime()
    }

    // add item

    // remove item
}