package com.example.testmyviewpager2.scrollabledynamictabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testmyviewpager2.*
import com.example.testmyviewpager2.databinding.ActivityDynamicViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

//better source
// https://stackoverflow.com/questions/61406176/viewpager2-not-able-to-dynamically-add-remove-fragment
//extra source just in case
// https://stackoverflow.com/questions/60130178/how-do-i-dynamically-add-and-remove-fragment-pages-using-viewpager2-and-mediator

// todo:
// remove tab by its name

// bug: kotlin: if i remove tabs too fast the app crashes (maybe have, try/catch block)

class DynamicViewPagerActivity : AppCompatActivity() {

    private var binding: ActivityDynamicViewPagerBinding? = null

    val activityViewPagerAdapter: DynamicViewPagerAdapter by lazy {
        DynamicViewPagerAdapter(this)} // todo: probably get rid of 'titles.size - 1'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicViewPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpTabs()
        addTabFabOnClick()
    }

    private fun setUpTabs() {
        binding?.dynamicViewPager?.offscreenPageLimit = 4
        binding?.dynamicViewPager?.adapter = activityViewPagerAdapter

        // Set the title of the tabs
        TabLayoutMediator(binding!!.dynamicTabLayout, binding!!.dynamicViewPager) { tab, position ->

            val (pairTitle, _) = titlesList[position]
            tab.text = pairTitle

        }.attach()
    }

    private fun addTabFabOnClick() {
        binding?.addTabFab?.setOnClickListener {
            val nextTitlePosition = titlesList.size - 1
//            val nextTitlePosition = titles.size - 1
            var nextTitle = testMovieTitles[nextTitlePosition]
            val numOfTabs = titlesList.size
            var titleIncrementer = 0 // to use the next tile until it doesn't match one of the tabs

            // new tabs cannot have the same name as old tabs
            while(pairsToList(titlesList).contains(nextTitle)) {
//            while(titles.contains(nextTitle)) {
                titleIncrementer++
                nextTitle = testMovieTitles[nextTitlePosition + titleIncrementer]
            }
            if(!pairsToList(titlesList).contains(nextTitle)) { activityViewPagerAdapter.addTab(nextTitle) }
//            if(!titles.contains(nextTitle)) { activityViewPagerAdapter.addTab(nextTitle) }

        }
    }
}
