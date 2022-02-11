package com.example.testroom.onetoone

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.testroom.onetoone.Data.OneToOneDatabase
import com.example.testroom.onetoone.Data.OneToOneRepository
import com.example.testroom.onetoone.Data.WorkoutOneToOne
import kotlinx.coroutines.launch

class OneToOneViewModel(application: Application): ViewModel() {

    private val TAG = "ViewModelTAG"
    private val roomDb = OneToOneDatabase.getDatabase(application)
    private val repository = OneToOneRepository(roomDb.oneToOneDao())

    fun insert(workout: WorkoutOneToOne) = viewModelScope.launch {

    }
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