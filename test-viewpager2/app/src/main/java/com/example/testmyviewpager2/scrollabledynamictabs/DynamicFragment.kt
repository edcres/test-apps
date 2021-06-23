package com.example.testmyviewpager2.scrollabledynamictabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.testmyviewpager2.*
import kotlinx.android.synthetic.main.fragment_dynamic.*

// todo: The text in the view will change depending on which tab is selected

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
        // get the adapter instance from the main activity
        fragmentViewPagerAdapter = (activity as? DynamicViewPagerActivity)?.activityViewPagerAdapter
        removeButtonOnClick()
        dynamic_fragment_text.text = titleToDisplay
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        Log.d("$MY_LOG", "onDestroy called on $titleToDisplay")
        super.onDestroy()
    }

    // todo: remove tab from the fragment to be removed
    // removes the last tab
    private fun removeButtonOnClick() {
        removeButton.setOnClickListener {
            // todo: possible bug: 'titleToDisplay' might be a bug here

//            val numOfTabs = titles.size
            val numOfTabs = titlesList.size
            if (numOfTabs > 1 && titleToDisplay != "All Movies") {
                fragmentViewPagerAdapter?.removeTab(titleToDisplay)
                //titles.remove(titleToDisplay)
            }
            //fragmentTransaction.remove(yourfragment).commit()
//            this.onDestroy()
            // possible gub: if I destroy this fragment manually, the last fragment might still be
            // destroyed bc it happens first. Maybe do it in the adapter
        }
    }

    fun setTitleText(title: String) {
        titleToDisplay = title
        // another bug is probably what calls this function
    }

    companion object{
        //The Fragment will then need to retrieve the Item from the List and display the content of
        // that item. Here is an example pager adapter.
        fun getInstance(titleId: Int): DynamicFragment {
            val thisDynamicFragment = DynamicFragment()

            val (titleToDisplay, _) = titlesList[titleId]
//            val titleToDisplay = titles[titleId]
            thisDynamicFragment.setTitleText(titleToDisplay)
            return thisDynamicFragment
        }
        fun getInstance(): DynamicFragment {
            val thisDynamicFragment = DynamicFragment()
            return thisDynamicFragment
        }
    }
}
