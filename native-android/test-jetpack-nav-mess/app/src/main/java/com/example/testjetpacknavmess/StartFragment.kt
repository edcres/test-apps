package com.example.testjetpacknavmess

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.testjetpacknavmess.databinding.FragmentStartBinding

class StartFragment : Fragment() {

//    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("startFrag__TAG", "onCreate: called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("startFrag__TAG", "onViewCreated: called")
//        sharedViewModel.setUpDatabase(requireActivity().application)
        return inflater.inflate(R.layout.fragment_start, container, false)
//        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
//        binding = fragmentBinding
//        return fragmentBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("startFrag__TAG", "onDestroy: called")
    }
}