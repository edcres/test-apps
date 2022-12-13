package com.example.testallnavigation.fragmentsnavcomnponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testallnavigation.R

/**
 * Safe Args
 *
 * https://www.youtube.com/watch?v=vx1-V3HH0IU
 *
 * https://www.raywenderlich.com/19327407-using-safe-args-with-the-android-navigation-component
 *
 * need to add the plugins
 */

class FragNavCompActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag_nav_comp)
    }
}