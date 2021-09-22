package com.aldreduser.my2_waydatabinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class StartingFragment : Fragment() {

    private lateinit var twoWayBindingFragBtn: Button
    private lateinit var mutableLiveDataBtn: Button
    private lateinit var livedataObserverBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_starting, container, false)

        bindUIWidgets(view)
        eventListeners(view)

        return view
    }

    private fun eventListeners(view: View) {
        // 'requireParentFragment().requireView()' gets the parent view bc this has the navHost
        //      there's probably a better way if doing it
        val navController = Navigation.findNavController(requireParentFragment().requireView())

        twoWayBindingFragBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_twoWayDataBngFragment)
        }
        mutableLiveDataBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_mutableLivedataFragment)
        }
        livedataObserverBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_mvvmObserverFragment)
        }
    }

    private fun bindUIWidgets(view: View) {
        twoWayBindingFragBtn = view.findViewById(R.id.two_way_binding_frag_btn)
        mutableLiveDataBtn = view.findViewById(R.id.mutable_live_data_btn)
        livedataObserverBtn = view.findViewById(R.id.livedata_observer_btn)
    }
}