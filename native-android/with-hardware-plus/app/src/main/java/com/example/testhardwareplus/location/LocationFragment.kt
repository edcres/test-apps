package com.example.testhardwareplus.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testhardwareplus.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.content.Intent
import android.widget.Toast
import android.location.Geocoder
import android.os.ResultReceiver
import android.util.Log

// https://www.tutorialspoint.com/the-simplest-way-to-get-the-user-s-current-location-on-android
// https://developer.android.com/training/location

class LocationFragment : Fragment() {

    private var addressResultReceiver: LocationAddressResultReceiver? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val TAG = "LocationFragTAG"
    private val LOCATION_PERMISSION_REQUEST_CODE = 2
    private var currentLocation: Location? = null
    private var locationCallback: LocationCallback? = null

    private lateinit var currentAddTv: TextView // locationTxt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        currentAddTv = view.findViewById(R.id.location_txt)

        addressResultReceiver = LocationAddressResultReceiver(requireContext(), Handler())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                currentLocation = locationResult.locations[0]
                getAddress()
            }
        }
        startLocationUpdates()

        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(
                    requireContext(), "Location permission not granted, " +
                            "restart the app if you want the feature", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "Fragment resumed")
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "Fragment paused")
        fusedLocationClient!!.removeLocationUpdates(locationCallback)
    }

    private fun startLocationUpdates() {
        val checkSelfPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // get the location (turn on location updates)
            val locationRequest = LocationRequest()
            locationRequest.interval = 2000
            locationRequest.fastestInterval = 1000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(
                requireContext(), "Can't find current address, ",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val intent = Intent(requireContext(), GetAddressIntentService::class.java)
        intent.putExtra("add_receiver", addressResultReceiver)
        intent.putExtra("add_location", currentLocation)

        requireActivity().startService(intent)
    }

    private inner class LocationAddressResultReceiver(context: Context, handler: Handler?) :
        ResultReceiver(handler) {

        val fragmentContext = context

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)

            if (resultCode == 0) {
                Log.d("Address", "Location null retrying")
                getAddress()
            }
            if (resultCode == 1) {
                Toast.makeText(fragmentContext, "Address not found, ", Toast.LENGTH_SHORT).show()
            }
            val currentAdd = resultData!!.getString("address_result")
            showResults(currentAdd!!)
        }

    }

    private fun showResults(currentAdd: String) {
        currentAddTv.text = currentAdd
    }

}
