package com.example.testroom.workoutstest1

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testroom.workoutstest1.data.WST1Database
import com.example.testroom.workoutstest1.data.WST1Repo
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WrkTst1ViewModel : ViewModel() {

    private val TAG = "ViewModel TAG"
    private lateinit var roomDb: WST1Database
    private lateinit var repository: WST1Repo

    private val _groups = MutableLiveData<MutableList<WST1Group>>()
    val groups: LiveData<MutableList<WST1Group>> get() = _groups
    // todo: 'workouts' will be organized by their 'position' attribute
    private val _workouts = MutableLiveData<MutableList<WST1Workout>>()
    val workouts: LiveData<MutableList<WST1Workout>> get() = _workouts
    private val _sets = MutableLiveData<MutableList<WST1Set>>()
    val sets: LiveData<MutableList<WST1Set>> get() = _sets

    fun startApplication(application: Application) {
        roomDb = WST1Database.getInstance(application)
        repository = WST1Repo(roomDb)
        fetchAllWorkouts()
    }

    // todo: I don't know if the workout variables in the repo are updated automatically.
    //      test that in the test app
    //      if they are then i don't need to update the livedata variables from here
    // DATABASE QUERIES //
    private fun fetchAllWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutGroups.collect {
                Log.d(TAG, "groups collected")
                _groups.postValue(it.toMutableList())
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkouts.collect {
                Log.d(TAG, "workouts collected")
                _workouts.postValue(it.toMutableList())
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutSets.collect {
                Log.d(TAG, "sets collected")
                _sets.postValue(it.toMutableList())
            }
        }
    }
    fun insertWorkoutGroup(workoutGroup: WST1Group) = viewModelScope.launch {
//        _groups.value!!.add(workoutGroup)
        repository.insert(workoutGroup)
    }




    fun insertWorkout(workout: WST1Workout): MutableLiveData<Long> {
        val itemId = MutableLiveData<Long>()
        viewModelScope.launch {
            itemId.postValue(repository.insert(workout))
            Log.d(TAG, "itemsId: ${itemId.value}")
        }
        return itemId
    }



//    fun insertWorkout(workout: WST1Workout) = viewModelScope.launch {
////        _workouts.value!!.add(workout)
//        val itemId = repository.insert(workout)
//        // todo: itemsId is fetched
//        Log.d(TAG, "itemsId: $itemId")
//    }
    fun insertWorkoutSet(workoutSet: WST1Set) = viewModelScope.launch {
//        _sets.value!!.add(workoutSet)
        repository.insert(workoutSet)
    }
    fun updateWorkout(workout: WST1Workout) = viewModelScope.launch {
        // todo:
        // update workout Workout entity and WorkoutSet entity
        Log.d(TAG, "workout to update: $workout")
        repository.updateWorkout(workout)
    }
    fun updateRep(set: WST1Set) {
        // todo:
    }
    fun updateWeight(set: WST1Set) {
        // todo:
    }
    fun removeSet(set: WST1Set) {
        // todo:
    }
    fun getWorkoutsOfThisGroup(group: WST1Group): List<WST1Workout> {
        // todo: do a query that gets all the workouts that are part of the group
        //      group = 'groupToDisplay'
        return mutableListOf()
    }
    fun getSetsOfThisWorkout(workoutName: String): List<WST1Set> {
        // todo
        return mutableListOf()
    }
    fun getNextSetNum(workoutName: String): Int {
        // todo: do a query that gets the next set in that workout
        //  check how many sets are part of that workout
        return 2
    }
    fun addGroupToWorkout(workout: WST1Workout) {
        // todo: add the 'groupSelected' to the workout_group in Workout Entity in the database
        //    (watch out for concurrency issues)
    }
    fun groupHasWorkouts(group: WST1Group): Boolean {
        // todo: call this function
        // todo: check if this group has any workouts
        // if not then delete group
        return true
    }

    // test functions
    fun fetchGroupsClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutGroups.collect {
                _groups.postValue(it.toMutableList())
            }
        }
    }
    fun fetchWorkoutsClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkouts.collect {
                _workouts.postValue(it.toMutableList())
            }
        }
    }
    fun fetchSetsClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutSets.collect {
                _sets.postValue(it.toMutableList())
            }
        }
    }
    // DATABASE QUERIES //
}