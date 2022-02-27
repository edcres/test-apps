package com.example.testroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class StartFragment : Fragment() {

    private lateinit var basicsBtn: Button
    private lateinit var oneToOneBtn: Button
    private lateinit var oneToManyBtn: Button
    private lateinit var manyToManyBtn: Button
    private lateinit var testWorkout1Btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        basicsBtn = view.findViewById(R.id.basics_btn)
        oneToOneBtn = view.findViewById(R.id.one_to_one_btn)
        oneToManyBtn = view.findViewById(R.id.one_to_many_btn)
        manyToManyBtn = view.findViewById(R.id.many_to_many_btn)
        testWorkout1Btn = view.findViewById(R.id.test_workout_1_btn)

        basicsBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_roomBasicsFragment)
        }
        oneToOneBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_roomOneToOneFragment)
        }
        oneToManyBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_oneToManyFragment)
        }
        manyToManyBtn.setOnClickListener {
            // todo:
        }
        testWorkout1Btn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_workoutsTest1Fragment)
        }

        return view
    }
}