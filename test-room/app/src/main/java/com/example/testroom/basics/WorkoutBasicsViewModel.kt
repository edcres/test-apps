package com.example.testroom.basics

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.testroom.basics.data.WorkoutBasics
import com.example.testroom.basics.data.WorkoutBasicsRepository
import com.example.testroom.basics.data.WorkoutBasicsRoomDatabase
import kotlinx.coroutines.launch
import kotlin.math.log

class WorkoutBasicsViewModel(application: Application): ViewModel() {

    private val TAG = "ViewModelTAG"
    private val roomDb = WorkoutBasicsRoomDatabase.getDatabase(application)
    private val repository = WorkoutBasicsRepository(roomDb.workoutBasicsDao())
    val allWorkouts: LiveData<List<WorkoutBasics>> = repository.allWorkouts.asLiveData()

    // HELPER //
    fun moveRecyclerItems(moveUp: Boolean, item: WorkoutBasics) = viewModelScope.launch {
        // Check if list is filled and if item can move up or down
        if(allWorkouts.value?.size!! > 1) {
            if(moveUp && item.position > 0) {
                // move up
                item.position --
                repository.moveItemReplaced(moveUp, item.position, item.position+1)
                updateWorkout(item)
            } else if (!moveUp && item.position < allWorkouts.value!!.size-1) {
                // move down
                item.position ++
                repository.moveItemReplaced(!moveUp, item.position, item.position-1)
                updateWorkout(item)
            }
        }
    }

    // DATABASE //
    fun insert(workoutBasics: WorkoutBasics) = viewModelScope.launch {
        repository.insert(workoutBasics)
    }
    fun deleteAllWorkouts() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun updateWorkout(workoutBasics: WorkoutBasics) = viewModelScope.launch {
        repository.updateWorkout(workoutBasics)
    }
    // DATABASE //
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