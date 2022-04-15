package com.example.testui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

/**
 * https://www.youtube.com/watch?v=lejBUeOSnf8
 * Create animation resource files
 * - translate
 * - set
 * - scale
 * - rotate
 * - alpha
 * Click the navGraph animation arrow and assign it a created animation
 *
 * Pop enter and pop exit is for when the user navigates up
 *
 */

class StartFragment : Fragment() {

    private lateinit var frag1Btn: Button
    private lateinit var frag2Btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val navController = Navigation.findNavController(requireParentFragment().requireView())

        frag1Btn = view.findViewById(R.id.frag_1_btn)
        frag2Btn = view.findViewById(R.id.frag_2_btn)

        frag1Btn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_oneFragment)
        }
        frag2Btn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_twoFragment)
        }

        return view
    }
}