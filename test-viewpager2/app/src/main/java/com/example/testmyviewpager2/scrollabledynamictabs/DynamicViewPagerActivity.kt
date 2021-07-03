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

// bug: kotlin: if i remove tabs too fast the app crashes
// bug: kotlin: maybe if i add tabs too fast, the wrong fragment is added
// bug: kotlin: eventually the titles run out, leads to indexOutOfBoundsException

class DynamicViewPagerActivity : AppCompatActivity() {

    private var binding: ActivityDynamicViewPagerBinding? = null
    val activityViewPagerAdapter: DynamicViewPagerAdapter by lazy {
        DynamicViewPagerAdapter(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicViewPagerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setUpTabs()
        addTabFabOnClick()
    }

    private fun setUpTabs() {
//        binding!!.dynamicViewPager.offscreenPageLimit = 4
        binding!!.dynamicViewPager.adapter = activityViewPagerAdapter

        // Set the title of the tabs
        TabLayoutMediator(binding!!.dynamicTabLayout, binding!!.dynamicViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun addTabFabOnClick() {
        binding!!.addTabFab.setOnClickListener {
            var titleIncrementer = 0 // to use the next tile until it doesn't match one of the tabs
            val nextTitlePosition = titles.size - 1
            val nextOrdinalId = titlesOrdinals.size - 1
            var nextTitle = testMovieTitles[nextTitlePosition]

            // if a title is the same as one of the current tabs, don't add it
            // new tabs cannot have the same name as old tabs
            while(titles.contains(nextTitle)) {
                titleIncrementer++
                nextTitle = testMovieTitles[nextTitlePosition + titleIncrementer]
            }
            if (titleIncrementer > 0) { Log.d("${MY_LOG}Activity", "incrementer: $titleIncrementer") }

            if(!titles.contains(nextTitle)) {
                activityViewPagerAdapter.addTab(nextOrdinalId+1, nextTitle)
            } else {
                Log.d("${MY_LOG}Activity", "\t\t titles contains next title " +
                        "\t\t $titles $nextTitle")
            }
        }
    }
}
