package com.example.testtestinganddi.basicshiltdagger2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testtestinganddi.R

/**
 * Field injection vs Constructor injection
 * - Constructor injection is better, field injection is simpler.
 * - The reason for dependency injection (Hilt/ Dagger2) is to make construction injection easier.
 */

class BasicsHiltAndDagger2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basics_hilt_and_dagger2, container, false)
    }
}