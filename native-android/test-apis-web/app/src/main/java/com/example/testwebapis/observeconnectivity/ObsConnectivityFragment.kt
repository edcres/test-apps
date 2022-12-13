package com.example.testwebapis.observeconnectivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.testwebapis.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/** Explanation of concept:
 *
 * There's a difference between being connected to a network and being connected to a network and having internet
 *  - This fragment only tests for being connected to a network
 *
 * To test having internet whole connected to a network:
 *      - you can ping a server and check if that's successful
 *          - https://www.youtube.com/watch?v=OEclG3XsPsg
 *
 * Then test cellular data
 *
 */

/**
 * This fragment
 *  Observe connectivity status (check connection to a network)
 *  https://www.youtube.com/watch?v=TzV0oCRDNfM
 *  This way only works on API (minSDK) lvl >=24
 */

class ObsConnectivityFragment : Fragment() {

    // normally this would be done in the viewModel
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This line should probably be in the ViewModel
        connectivityObserver = NetworkConnectivityObserver(requireActivity().applicationContext)

        connectivityObserver.observe().onEach {
            // todo: do stuff
            println("Status is $it")
        }.launchIn(lifecycleScope)  // Can use a different scope
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_obs_connectivity, container, false)
    }
}