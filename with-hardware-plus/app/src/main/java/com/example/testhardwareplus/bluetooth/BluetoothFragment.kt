package com.example.testhardwareplus.bluetooth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.testhardwareplus.R

class BluetoothFragment : Fragment() {

    private lateinit var bleFragBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bluetooth, container, false)

        bleFragBtn = view.findViewById(R.id.ble_frag_btn)

        bleFragBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_bluetoothFragment_to_BLEFragment)
        }

        return view
    }

}