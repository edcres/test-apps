package com.example.testmyviewpager2.scrollabledynamictabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testmyviewpager2.databinding.ActivityDynamicViewPagerBinding
import com.example.testmyviewpager2.testMovieTitles
import com.example.testmyviewpager2.titles
import com.google.android.material.tabs.TabLayoutMediator

//better source
// https://stackoverflow.com/questions/61406176/viewpager2-not-able-to-dynamically-add-remove-fragment
//extra source just in case
// https://stackoverflow.com/questions/60130178/how-do-i-dynamically-add-and-remove-fragment-pages-using-viewpager2-and-mediator

// I'm not using a viewpager here in order to keep it simple

// todo:
// change fragment text so it reflects the tab title
// use a fab in the holder activity

class DynamicViewPagerActivity : AppCompatActivity() {

    private var binding: ActivityDynamicViewPagerBinding? = null
    val viewPagerAdapter: DynamicViewPagerAdapter by lazy {
        DynamicViewPagerAdapter(this, titles.size - 1)} // todo: probably get rid of 'titles.size - 1'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicViewPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpTabs()
    }

    private fun setUpTabs() {
        binding?.dynamicViewPager?.offscreenPageLimit = 4
        binding?.dynamicViewPager?.adapter = viewPagerAdapter

        TabLayoutMediator(binding!!.dynamicTabLayout, binding!!.dynamicViewPager) { tab, position ->
            // Set the title of the tab
            tab.text = titles[position]
            //probably delete this
//            tab.text = when (titles[position]) {
//                // naming the tabs
//                titles[0] -> position.toString()  //titles[0]
//                titles[1] -> titles[1]
//                titles[2] -> titles[2]
//                titles[3] -> titles[3]
//                else -> titles[0]
//            }
        }.attach()
    }
}
