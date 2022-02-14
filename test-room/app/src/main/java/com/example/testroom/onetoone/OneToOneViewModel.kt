package com.example.testroom.onetoone

import android.app.Application
import androidx.lifecycle.*
import com.example.testroom.onetoone.data.OneToOneDatabase
import com.example.testroom.onetoone.data.OneToOneRepository
import com.example.testroom.onetoone.data.entities.CarOneToOne
import com.example.testroom.onetoone.data.entities.PersonAndCarOneToOne
import com.example.testroom.onetoone.data.entities.PersonOneToOne
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OneToOneViewModel(application: Application): ViewModel() {

    private val TAG = "ViewModelTAG"
    private val roomDb = OneToOneDatabase.getDatabase(application)
    private val repository = OneToOneRepository(roomDb.oneToOneDao())
    // todo: uncomment this
    var allPersonsAndCars: List<PersonAndCarOneToOne>? = null

    fun setPsAnsCsVar() {
        CoroutineScope(Dispatchers.IO).launch {
            allPersonsAndCars = repository.getAllPersonsAndCars()
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