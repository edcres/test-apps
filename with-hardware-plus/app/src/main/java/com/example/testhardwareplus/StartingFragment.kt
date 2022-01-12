package com.example.testhardwareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

/*
 * 'Classic Bluetooth' (as opposed to BLE) uses include streaming
 *
 * Using the Bluetooth APIs, an app can perform the following:
 *      -Scan for other Bluetooth devices.
 *      -Query the local Bluetooth adapter for paired Bluetooth devices.
 *      -Establish RFCOMM channels.
 *      -Connect to other devices through service discovery.
 *      -Transfer data to and from other devices.
 *      -Manage multiple connections.
 *
 *  To use:
 *      -declare permissions
 *      -access the BluetoothAdapter and determine if Bluetooth is available on the device.
 *          If Bluetooth is available, there are three steps to make a connection:
 *            1- Find nearby Bluetooth devices, either devices that are already paired or new ones.
 *            2- Connect to a Bluetooth device.
 *            3- Transfer data with the connected device.
 *
 *
 */

class StartingFragment : Fragment() {

    private lateinit var cameraBtn: Button
    private lateinit var notificationBtn: Button
    private lateinit var bluetoothBtn: Button
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
        notificationBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_startingFragment_to_bluetoothFragment)
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
        bluetoothBtn = view.findViewById(R.id.bluetooth_btn)
//        mapBtn = view.findViewById(R.id.map_btn)
    }
}
