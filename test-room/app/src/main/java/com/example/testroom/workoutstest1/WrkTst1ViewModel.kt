package com.example.testroom.workoutstest1

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

    private lateinit var roomDb: WST1Database
    private lateinit var repository: WST1Repo

    private val _groups = MutableLiveData<MutableList<WST1Group>>()
    val groups: LiveData<MutableList<WST1Group>> get() = _groups
    private val _workouts = MutableLiveData<MutableList<WST1Workout>>()
    val workouts: LiveData<MutableList<WST1Workout>> get() = _workouts
    private val _sets = MutableLiveData<MutableList<WST1Set>>()
    val sets: LiveData<MutableList<WST1Set>> get() = _sets

    // todo: I don't know if the workout variables in the repo are updated automatically.
    //      test that in the test app
    //      if they are then i don't need to update the livedata variables from here
    // DATABASE QUERIES //
    private fun fetchAllWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutGroups.collect {
                _groups.postValue(it.toMutableList())
            }
            repository.allWorkouts.collect {
                _workouts.postValue(it.toMutableList())
            }
            repository.allWorkoutSets.collect {
                _sets.postValue(it.toMutableList())
            }
        }
    }
    fun insertWorkoutGroup(workoutGroup: WST1Group) = viewModelScope.launch {
        _groups.value!!.add(workoutGroup)
        repository.insert(workoutGroup)
    }
    fun insertWorkout(workout: WST1Workout) = viewModelScope.launch {
        _workouts.value!!.add(workout)
        repository.insert(workout)
    }
    fun insertWorkoutSet(workoutSet: WST1Set) = viewModelScope.launch {
        _sets.value!!.add(workoutSet)
        repository.insert(workoutSet)
    }
    fun updateTitle(workout: WST1Workout) {
        // todo:
        // update workout Workout entity and  WorkoutSet entity
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
    // DATABASE QUERIES //
}