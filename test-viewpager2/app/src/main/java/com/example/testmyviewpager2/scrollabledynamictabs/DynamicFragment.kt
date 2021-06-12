package com.example.testmyviewpager2.scrollabledynamictabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testmyviewpager2.R
import com.example.testmyviewpager2.titles
import kotlinx.android.synthetic.main.fragment_dynamic.*

// todo: The text in the view will change depending on which tab is selected

class DynamicFragment : Fragment() {

    //delete this
    private var textView: TextView? = null

    // todo: possible null pointer bug
    private val viewPagerActivity = DynamicViewPagerActivity() // delete this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textView = dynamic_fragment_text
        buttonOnClick()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun buttonOnClick() {
        button.setOnClickListener{
//            textView?.text = titles.size.toString()
//            viewPagerActivity.addTitle()
        }
    }
}
