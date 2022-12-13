package com.example.testmyviewpager2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.testmyviewpager2.fixedtabs.FragmentViewPagerActivity
import com.example.testmyviewpager2.scrollabledynamictabs.DynamicViewPagerActivity

// Viewpager2, Fragments, Tabs, Material Design
// https://www.androidhive.info/2020/01/viewpager2-pager-transformations-intro-slider-pager-animations-pager-transformations/
// setPageTransformer() to change the animation of transitions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fixedTabsOnClick()
        dynamicTabsOnClick()
    }

    private fun fixedTabsOnClick() {
        val fixedTabsButton = findViewById<Button>(R.id.fixed_tabs_button)
        fixedTabsButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, FragmentViewPagerActivity::class.java))
        }
    }

    private fun dynamicTabsOnClick() {
        val dynamicTabsButton = findViewById<Button>(R.id.dynamic_tabs_button)
        dynamicTabsButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, DynamicViewPagerActivity::class.java))
        }
    }
}

// steps:
/*
- add dependency:   implementation "androidx.viewpager2:viewpager2:1.0.0"
- add fragments
- add fragments.xml
- add viewpager activity
- add viewpager activity.xml
- add viewpager adapter (inside the viewpager activity here)
 */