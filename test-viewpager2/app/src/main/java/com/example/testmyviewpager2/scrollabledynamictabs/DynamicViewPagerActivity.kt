package com.example.testmyviewpager2.scrollabledynamictabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testmyviewpager2.R
import com.example.testmyviewpager2.databinding.ActivityDynamicViewPagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// I'm not using a viewpager here in order to keep it simple

class DynamicViewPagerActivity : AppCompatActivity() {
    var binding: ActivityDynamicViewPagerBinding? = null

    // todo: This array should be mutable.
    private val titles = arrayOf("All Workouts")

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
}