package com.example.testroom.onetomany

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.testroom.R
import com.example.testroom.databinding.FragmentOneToManyBinding
import com.example.testroom.onetomany.data.OneToManyDao
import com.example.testroom.onetomany.data.OneToManyDatabase
import com.example.testroom.onetomany.data.entities.Director
import com.example.testroom.onetomany.data.entities.School
import com.example.testroom.onetomany.data.entities.SchoolAndDirector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OneToManyFragment : Fragment() {

    private val fragmentTAG = "OneToManyFragTAG"
    private var binding: FragmentOneToManyBinding? = null
    private lateinit var dao: OneToManyDao
    private var _schoolsAndDirectors = MutableLiveData<MutableList<SchoolAndDirector>>()

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
                if (directorEt.text.isNotEmpty() && schoolEt.text.isNotEmpty()) {
                    Log.i(fragmentTAG, "save pressed")
                    CoroutineScope(Dispatchers.IO).launch {
                        dao.insertSchool(School(schoolName = schoolEt.text.toString()))
                        dao.insertDirector(
                            Director(
                                directorName = directorEt.text.toString(),
                                schoolName = schoolEt.text.toString()
                            )
                        )
                        withContext(Dispatchers.Main) {
                            schoolEt.text.clear()
                            directorEt.text.clear()
                        }
                    }
                } else Log.i(fragmentTAG, "Type something")
            }

            deleteAllBtn.setOnClickListener {
                Log.i(fragmentTAG, "delete all pressed")
                CoroutineScope(Dispatchers.IO).launch {
                    dao.deleteAllSchool()
                    dao.deleteAllDirector()
                }
            }

            actionBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {

                    dao.getSchoolsAndDirectors().collect {
                        _schoolsAndDirectors.postValue(it.toMutableList())
                        Log.d(fragmentTAG, "schoolsAndDirectors size: ${it.size}")
                        for(i in 0 until it.size) {
                            Log.d(fragmentTAG, "schoolsAndDirectors: \n ${it[i]}")
                        }
                    }
                }
            }
        }
    }
}
