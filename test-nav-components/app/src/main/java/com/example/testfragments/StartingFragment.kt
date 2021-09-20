package com.example.testfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class StartingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_starting, container, false)

        val basicFragmentsBtn = view.findViewById<Button>(R.id.basic_fragments_btn)
        basicFragmentsBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startingFragment_to_firstFragment)
        }

        return view
    }
}