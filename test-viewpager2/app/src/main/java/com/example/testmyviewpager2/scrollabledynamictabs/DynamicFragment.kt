package com.example.testmyviewpager2.scrollabledynamictabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testmyviewpager2.*
import kotlinx.android.synthetic.main.fragment_dynamic.*

class DynamicFragment : Fragment() {

    private var fragmentViewPagerAdapter: DynamicViewPagerAdapter? = null
    private var titleToDisplay = "All Movies"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get the adapter instance from the main activity
        fragmentViewPagerAdapter = (activity as? DynamicViewPagerActivity)!!.activityViewPagerAdapter
        removeButtonOnClick()
        dynamic_fragment_text.text = titleToDisplay
        Log.d("${MY_LOG}fragCreated", "name: ${titleToDisplay}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("${MY_LOG}destroyed", "\t\t\t $titles")
        Log.d("${MY_LOG}destroyed", "\t\t\t $titlesOrdinals")
    }

    private fun removeButtonOnClick() {
        removeButton.setOnClickListener {
            val numOfTabs = titles.size
            if (numOfTabs > 1 && titleToDisplay != "All Movies") {
                fragmentViewPagerAdapter!!.removeTab(titleToDisplay)
            }
        }
    }

    companion object{
        // The Fragment retrieves the Item from the List and displays the content of that item.
        fun getInstance(titleId: Int): DynamicFragment {
            val thisDynamicFragment = DynamicFragment()
            val titleToDisplay = titles[titleId]
            thisDynamicFragment.titleToDisplay = titleToDisplay
//            thisDynamicFragment.setTitleText(titleToDisplay)
            return thisDynamicFragment
        }
    }
}
