package com.example.testwebapis.observeconnectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver (private val context: Context) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        // take this callback (connectivityManager) and converted to a Flow
        return callbackFlow {
            // use this to send values when a specific callback function is triggered
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }
            }

            // the coroutine scope used to collect this Flow will handle unregistering and registering this callback
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                // called when the user navigates away (and viewModel scope will be cancelled)
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    // 'distinctUntilChanged()' will make sure that if we have 2 emissions of the same type that come one after
    //      another, the callback flow wont trigger. Now it will only trigger if there's a change in status.
    }
}