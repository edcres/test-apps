package com.example.testwebapis.rndcats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwebapis.rndcats.network.CatPhoto
import com.example.testwebapis.rndcats.network.CatsApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class CatsApiStatus { LOADING, ERROR, DONE }

class RandomCatsViewModel: ViewModel() {

    private val _status = MutableLiveData<CatsApiStatus>()
    val status: LiveData<CatsApiStatus> = _status

    private val _photos = MutableLiveData<List<CatPhoto>>()
    val photos: LiveData<List<CatPhoto>> = _photos

    init {
        getCatPhotos()
    }

    // Gets photos information from the Mars API Retrofit service and updates the
    // [CatPhoto] [List] [LiveData].
    private fun getCatPhotos() {
        viewModelScope.launch {
            _status.value = CatsApiStatus.LOADING
            try {
                _photos.value = CatsApi.catsApiService.getPhotos()
                _status.value = CatsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CatsApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}