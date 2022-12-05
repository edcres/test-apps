package com.example.testrecyclerview.widgetsinterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testrecyclerview.R

// This is a recyclerview that uses multiple widgets with click listeners in each viewHolder (interface)

private const val TAG = "ManyClicksFrag__TAG"

class ClickInterfaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_widgets_interface, container, false)
    }
}