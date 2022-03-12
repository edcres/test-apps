package com.aldreduser.testapp.recyclerinrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.FIRST_TAB_TITLE
import com.aldreduser.testapp.R
import com.aldreduser.testapp.basicrecyclerview.entities.Workout
import com.aldreduser.testapp.basicrecyclerview.entities.WorkoutSet

class NestedRecyclerActivity : AppCompatActivity() {

    private lateinit var workoutsAdapter: WorkoutsAdapter
    private lateinit var workoutsRecyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler)

        workoutsRecyclerview = findViewById(R.id.workouts_recyclerview)

        workoutsAdapter = WorkoutsAdapter(this, this, startingSets())
        workoutsRecyclerview.adapter = workoutsAdapter
        workoutsRecyclerview.layoutManager = GridLayoutManager(this, 2)
        workoutsAdapter.submitList(startingWorkouts())
    }

    private fun startingWorkouts(): List<Workout> {
        return listOf(
            Workout(0, "wor1", FIRST_TAB_TITLE),
            Workout(1, "wor2", FIRST_TAB_TITLE),
            Workout(2, "wor3", FIRST_TAB_TITLE),
            Workout(3, "wor4", FIRST_TAB_TITLE),
            Workout(4, "wor5", FIRST_TAB_TITLE)
        )
    }

    private fun startingSets(): List<WorkoutSet> {
        return listOf(
            WorkoutSet(0, 0, "wor1", 1, 0, 0.0),
            WorkoutSet(1, 0, "wor2", 2, 0, 0.0),
            WorkoutSet(2, 0, "wor3", 3, 0, 0.0),
            WorkoutSet(3, 0, "wor4", 4, 0, 0.0)
        )
    }
}