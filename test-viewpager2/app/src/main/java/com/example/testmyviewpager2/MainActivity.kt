package com.example.testmyviewpager2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.testmyviewpager2.fixedtabs.FragmentViewPagerActivity

// Viewpager2, Fragments, Tabs, Material Design
// https://www.androidhive.info/2020/01/viewpager2-pager-transformations-intro-slider-pager-animations-pager-transformations/
// setPageTransformer() to change the animation of transitions

class MainActivity : AppCompatActivity() {

    private val fixedTabsButton = findViewById<Button>(R.id.fixed_tabs_button)
    private val dynamicTabsButton = findViewById<Button>(R.id.dynamic_tabs_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this@MainActivity, FragmentViewPagerActivity::class.java))
    }

    private fun fixedTabsOnClick() {
        fixedTabsButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, FragmentViewPagerActivity::class.java))
        }
    }

    // todo: make dynamic tabs activity
    private fun dynamicTabsOnClick() {
        dynamicTabsButton.setOnClickListener {
            //startActivity(Intent(this@MainActivity, ::class.java))
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