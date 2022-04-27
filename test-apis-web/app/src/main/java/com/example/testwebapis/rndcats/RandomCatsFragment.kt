package com.example.testwebapis.rndcats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testwebapis.R

/**
 * Simple API use, app displays pictures of cats in a recyclerview
 *
 * loading images on a background thread and caching loaded images
 *
 *
 */

/** Notes:
 *
 * A JSON object is a collection of key-value pairs.
 * Retrofit ultimately creates most of the network layer for you, including critical details such as running the requests on background threads.
 */

class RandomCatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_random_cats, container, false)
    }
}