package com.example.testmyviewpager2.scrollabledynamictabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testmyviewpager2.R
import com.example.testmyviewpager2.testMovieTitles
import com.example.testmyviewpager2.titles
import kotlinx.android.synthetic.main.fragment_dynamic.*

// todo: The text in the view will change depending on which tab is selected

class DynamicFragment : Fragment() {

    //delete this
    private var textView: TextView? = null
    private val holderActivity = DynamicViewPagerActivity()
    private val viewPagerAdapter: DynamicViewPagerAdapter by lazy {DynamicViewPagerAdapter(holderActivity)}

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
        addButtonOnClick()
        removeButtonOnClick()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addButtonOnClick() {
        addButton.setOnClickListener {
            val numOfTabs = titles.size
            val nextTitle = testMovieTitles[numOfTabs - 1]
            viewPagerAdapter.addTab(nextTitle)
//            textView?.text = titles.size.toString()
//            viewPagerActivity.addTitle()
        }
    }

    // removes the last tab
    private fun removeButtonOnClick() {
        removeButton.setOnClickListener {
            val numOfTabs = titles.size
            val lastTabPositionInArray = numOfTabs - 1
            viewPagerAdapter.removeTab(lastTabPositionInArray)
        }
    }

    fun setTitleText(text: String) {
        textView?.text = text
    }

    companion object{
        //The Fragment will then need to retrieve the Item from the List and display the content of
        // that item. Here is an example pager adapter.
        fun getInstance(titleId: Int): DynamicFragment {

            val titleToDisplay = titles[titleId]
            DynamicFragment().setTitleText(titleToDisplay)

            return DynamicFragment as DynamicFragment
        }
    }
}
