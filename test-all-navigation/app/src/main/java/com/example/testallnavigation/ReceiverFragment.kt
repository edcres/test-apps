package com.example.testallnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class ReceiverFragment : Fragment() {

    private lateinit var screen2_number_txt: TextView
    private val args: ReceiverFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_receiver, container, false)

        screen2_number_txt = view.findViewById(R.id.screen2_number_txt)

        val myNumber = args.number
        screen2_number_txt.text = myNumber.toString()

        return view
    }
}