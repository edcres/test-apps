package com.example.testcoroutines.parallelasyncawait

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testcoroutines.R

// https://www.youtube.com/watch?v=HPpiPzwQ_cU

class ParallelAsyncAwait : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parallel_async_await, container, false)
    }
}