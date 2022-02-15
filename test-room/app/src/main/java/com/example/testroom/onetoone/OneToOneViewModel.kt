package com.example.testroom.onetoone

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.testroom.onetoone.data.OneToOneDatabase
import com.example.testroom.onetoone.data.OneToOneRepository
import com.example.testroom.onetoone.data.entities.CarOneToOne
import com.example.testroom.onetoone.data.entities.PersonAndCarOneToOne
import com.example.testroom.onetoone.data.entities.PersonOneToOne
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

class OneToOneViewModel(application: Application): ViewModel() {

    private val TAG = "ViewModelTAG"
    private val roomDb = OneToOneDatabase.getDatabase(application)
    private val repository = OneToOneRepository(roomDb.oneToOneDao)
    private var _allPersonsAndCars = MutableLiveData<MutableList<PersonAndCarOneToOne>>()
    val allPersonsAndCars: LiveData<MutableList<PersonAndCarOneToOne>> get() = _allPersonsAndCars
    private var _allPersons = MutableLiveData<MutableList<PersonOneToOne>>()
    val allPersons: LiveData<MutableList<PersonOneToOne>> get() = _allPersons

    init {
        Log.d(TAG, "ViewModel init")
        setStoredToLiveData()
    }

    // HELPER //
    private fun setStoredToLiveData() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allPersonsAndCars.collect {
                Log.d(TAG, "pc collect happens")
                _allPersonsAndCars.postValue(it.toMutableList())
                Log.d(TAG, "vm: allPersonsAndCars\n$it")
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.allPersons.collect {
                Log.d(TAG, "p collect happens")
                _allPersons.postValue(it.toMutableList())
                Log.d(TAG, "vm: allPersons\n$it")
            }
        }
    }

    // DATABASE //
    fun insert(person: PersonOneToOne) = viewModelScope.launch {
        repository.insertPerson(person)
    }
    fun insert(car: CarOneToOne) = viewModelScope.launch {
        repository.insertCar(car)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    // DATABASE //
}

class OneToOneViewModelFactory(var application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneToOneViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OneToOneViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}