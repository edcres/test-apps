package com.example.testroom.onetomany

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.testroom.databinding.FragmentOneToManyBinding
import com.example.testroom.onetomany.data.OneToManyDao
import com.example.testroom.onetomany.data.OneToManyDatabase
import com.example.testroom.onetomany.data.entities.Group
import com.example.testroom.onetomany.data.entities.Workout
import com.example.testroom.onetomany.data.entities.GroupAndWorkout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// The child entity must include a variable that is a reference to the
//      primary key of the parent entity.

// With parentColumn set to the name of the primary key column of the parent
//      entity and entityColumn set to the name of the column of the child
//      entity that references the parent entity's primary key.

// todo: find a good way to get the foreign key into the Workout entity.
// maybe the answer is here: https://www.youtube.com/watch?v=DyEmgDTQmxw

class OneToManyFragment : Fragment() {

    private val fragmentTAG = "OneToManyFragTAG"
    private var binding: FragmentOneToManyBinding? = null
    private lateinit var dao: OneToManyDao
    private var _groupAndWorkouts = MutableLiveData<MutableList<GroupAndWorkout>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentOneToManyBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = OneToManyDatabase.getDatabase(requireContext()).oneToManyDao
        binding?.apply {
            saveBtn.setOnClickListener {
                if (groupEt.text.isNotEmpty() && workoutEt.text.isNotEmpty()) {
                    Log.i(fragmentTAG, "save pressed")
                    CoroutineScope(Dispatchers.IO).launch {


                        /**
                         * TODO: to figure out another time
                         *
                         * I need to get the group id when I set the group to th db, the use that
                         *      as a foreign key in the Workout entity.
                         * - The obvious solution is to make a separate query and get the Group id
                         * - But I think there is a better solution. (the correct solution)
                         */

                        // todo: get the group id concurrently
                        dao.insertGroup(Group(groupName = groupEt.text.toString()))
//                        dao.insertWorkout(
//                            Workout(
//                                workoutName = workoutEt.text.toString(),
//                                groupId = groupId
//                            )
//                        )




                        withContext(Dispatchers.Main) {
                            groupEt.text.clear()
                            workoutEt.text.clear()
                        }
                    }
                } else Log.i(fragmentTAG, "Type something")
            }

            deleteAllBtn.setOnClickListener {
                Log.i(fragmentTAG, "delete all pressed")
                CoroutineScope(Dispatchers.IO).launch {
                    dao.deleteAllGroups()
                    dao.deleteAllWorkouts()
                }
            }

            actionBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {

                    dao.getGroupsAndWorkouts().collect {
                        _groupAndWorkouts.postValue(it.toMutableList())
                        Log.d(fragmentTAG, "getGroupsAndWorkouts size: ${it.size}")
                        for(i in 0 until it.size) {
                            Log.d(fragmentTAG, "getGroupsAndWorkouts: \n ${it[i]}")
                        }
                    }
                }
            }
        }
    }
}
