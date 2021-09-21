package com.aldreduser.my2_waydatabinding

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

        val twoWayBindingFragBtn = view.findViewById<Button>(R.id.two_way_binding_frag_btn)
        val basicObserverBtn = view.findViewById<Button>(R.id.basic_observer_btn)
        val mutableLiveDataBtn = view.findViewById<Button>(R.id.mutable_live_data_btn)
        twoWayBindingFragBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startingFragment_to_twoWayDataBngFragment)
        }
        basicObserverBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startingFragment_to_basicObserverFragment)
        }
        mutableLiveDataBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startingFragment_to_mutableLivedataFragment)
        }

        return view
    }
}