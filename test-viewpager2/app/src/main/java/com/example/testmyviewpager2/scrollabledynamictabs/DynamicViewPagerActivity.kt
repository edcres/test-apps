package com.example.testmyviewpager2.scrollabledynamictabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testmyviewpager2.databinding.ActivityDynamicViewPagerBinding
import com.example.testmyviewpager2.testMovieTitles
import com.example.testmyviewpager2.titles
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//better source
// https://stackoverflow.com/questions/61406176/viewpager2-not-able-to-dynamically-add-remove-fragment
//extra source just in case
// https://stackoverflow.com/questions/60130178/how-do-i-dynamically-add-and-remove-fragment-pages-using-viewpager2-and-mediator

// I'm not using a viewpager here in order to keep it simple

// add more tabs dynamically
// display the tab title inside each respective fragment
// use a fab in the holder activity

class DynamicViewPagerActivity : AppCompatActivity() {

    var binding: ActivityDynamicViewPagerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicViewPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpTabs()
    }

    private fun setUpTabs() {
        binding?.dynamicViewPager?.adapter = DynamicViewPagerAdapter(this)

        // attaching tab mediator
        binding?.let { TabLayoutMediator(binding!!.dynamicTabLayout, it.dynamicViewPager
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = titles[position]
            }.attach()
        }
    }

//    fun addTitle() {
//        // Get the next String in 'movieTitles'
//        // and add it as a tab
//        val movieToGet = titles.size - 1
//        titles.add(testMovieTitles[movieToGet])
//
//        // todo: update the recycler view with new tabs
//        setUpTabs()
//    }
}
