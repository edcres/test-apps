package com.example.testmyviewpager2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testmyviewpager2.R

// Viewpager2, Fragments, Tabs, Material Design
// https://www.androidhive.info/2020/01/viewpager2-pager-transformations-intro-slider-pager-animations-pager-transformations/
// setPageTransformer() to change the animation of transitions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this@MainActivity, FragmentViewPagerActivity::class.java))
    }
}

// steps:
/*
- add dependency:   implementation "androidx.viewpager2:viewpager2:1.0.0"
- add fragments
- add fragments.xml
- add viewpager activity
- add viewpager activity.xml
 */