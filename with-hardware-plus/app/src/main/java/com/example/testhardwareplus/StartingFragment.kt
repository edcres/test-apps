package com.example.testhardwareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class StartingFragment : Fragment() {

    private lateinit var cameraBtn: Button
    private lateinit var notificationBtn: Button
//    private lateinit var mapBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_starting, container, false)

        bindUIWidgets(view)

        cameraBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startingFragment_to_cameraFragment)
        }
        notificationBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startingFragment_to_notificationFragment)
        }
//        mapBtn.setOnClickListener {
//            Navigation.findNavController(view)
//                .navigate(R.id.action_startingFragment_to_mapFragment)
//        }

        return view
    }

    private fun bindUIWidgets(view: View) {
        cameraBtn = view.findViewById(R.id.camera_btn)
        notificationBtn = view.findViewById(R.id.notification_btn)
//        mapBtn = view.findViewById(R.id.map_btn)
    }
}