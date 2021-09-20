package com.aldreduser.my2_waydatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.aldreduser.my2_waydatabinding.databinding.ActivityMainBinding
import com.aldreduser.my2_waydatabinding.twowaydatabinding.TwoWayDataBngViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val workoutsListViewModel: TwoWayDataBngViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.apply {
            lifecycleOwner = this@MainActivity

        }

    }

}
