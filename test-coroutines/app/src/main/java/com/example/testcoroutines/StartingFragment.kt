package com.example.testcoroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class StartingFragment : Fragment() {

    private lateinit var asyncAwaitBtn: Button
    private lateinit var parallelAsyncAwaitBtn: Button
    private lateinit var basicCoroutinesBtn: Button
    private lateinit var dialogAwaitBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_starting, container, false)
        setUIWidgets(view)
        eventListeners()
        return view
    }

    private fun eventListeners() {
        val navController = Navigation.findNavController(requireParentFragment().requireView())

        asyncAwaitBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_asyncAwaitFragment)
        }
        parallelAsyncAwaitBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_parallelAsyncAwait)
        }
        basicCoroutinesBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_basicCoroutines)
        }
        dialogAwaitBtn.setOnClickListener {
            navController.navigate(R.id.action_startingFragment_to_dialogAwaitFragment)
        }
    }

    private fun setUIWidgets(view: View) {
        asyncAwaitBtn = view.findViewById(R.id.async_await_btn)
        parallelAsyncAwaitBtn = view.findViewById(R.id.parallel_async_await_btn)
        basicCoroutinesBtn = view.findViewById(R.id.basic_coroutines_btn)
        dialogAwaitBtn = view.findViewById(R.id.dialog_await_btn)
    }
}