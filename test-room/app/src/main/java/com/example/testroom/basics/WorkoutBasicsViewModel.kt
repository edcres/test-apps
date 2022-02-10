package com.example.testroom.basics

import android.app.Application
import androidx.lifecycle.*
import com.example.testroom.basics.data.WorkoutBasics
import com.example.testroom.basics.data.WorkoutBasicsRepository
import com.example.testroom.basics.data.WorkoutBasicsRoomDatabase
import kotlinx.coroutines.launch

class WorkoutBasicsViewModel(application: Application): ViewModel() {

    private val roomDb = WorkoutBasicsRoomDatabase.getDatabase(application)
    private val repository = WorkoutBasicsRepository(roomDb.workoutBasicsDao())
    val allWorkouts: LiveData<List<WorkoutBasics>> = repository.allWorkouts.asLiveData()

    // Launching a new coroutine to insert the data in a non-blocking way.
    fun insert(workoutBasics: WorkoutBasics) = viewModelScope.launch {
        repository.insert(workoutBasics)
    }

    fun deleteAllWorkouts() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class WorkoutBasicsViewModelFactory(var application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutBasicsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutBasicsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}