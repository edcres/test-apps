package com.example.testallnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation

class SenderFragment : Fragment() {

    private lateinit var screenNumberTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sender, container, false)

        screenNumberTxt = view.findViewById(R.id.screen_number_txt)

        screenNumberTxt.setOnClickListener {
            val action = SenderFragmentDirections.actionSenderFragmentToReceiverFragment(2)
//            val action = SenderFragmentDirections.actionSenderFragmentToReceiverFragment()
            Navigation.findNavController(requireParentFragment().requireView())
                .navigate(action)
        }

        return view
    }

}