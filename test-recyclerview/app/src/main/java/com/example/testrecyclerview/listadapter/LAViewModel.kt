package com.example.testrecyclerview.listadapter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "LAFrag_TAG"

class LAViewModel: ViewModel() {

    var lastItemID = 5

    private val _basicItems = MutableLiveData<MutableList<BasicRecyclerItem>>()
    val basicItems: LiveData<MutableList<BasicRecyclerItem>> get() = _basicItems

    init {
        _basicItems.postValue(fillUpBasicItems(5))
    }

    private fun fillUpBasicItems(size: Int): MutableList<BasicRecyclerItem> {
        val itemsList = mutableListOf<BasicRecyclerItem>()
        for (i in 1..size) {
            val thisItem = BasicRecyclerItem("Item #$i", "Subtext $i")  //position in mutableList()
            itemsList.add(thisItem)
        }
        return itemsList
    }

    fun removeItemAt(position: Int) {
        if (_basicItems.value != null) {
            if (_basicItems.value!!.size > position) {
                _basicItems.value!!.removeAt(position)
                _basicItems.postValue(_basicItems.value)
            }
        }
    }

    fun addItem() {
        if (_basicItems.value != null) {
            lastItemID++
            _basicItems.value!!
                .add( BasicRecyclerItem("Item #$lastItemID", "Subtext $lastItemID"))
            _basicItems.postValue(_basicItems.value)
        }
    }
}