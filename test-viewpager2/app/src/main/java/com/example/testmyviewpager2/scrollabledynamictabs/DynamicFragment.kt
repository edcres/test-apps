package com.example.testmyviewpager2.scrollabledynamictabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testmyviewpager2.R

// todo: The text in the view will change depending on which tab is selected

class DynamicFragment : Fragment() {

    // todo: possible null pointer bug
    private val dynamicTextView = view?.findViewById<TextView>(R.id.dynamic_fragment_text)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
