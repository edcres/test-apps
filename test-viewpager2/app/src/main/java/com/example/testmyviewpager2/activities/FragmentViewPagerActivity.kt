package com.example.testmyviewpager2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testmyviewpager2.databinding.ActivityFragmentViewPagerBinding
import com.example.testmyviewpager2.fragments.EventsFragment
import com.example.testmyviewpager2.fragments.MoviesFragment
import com.example.testmyviewpager2.fragments.TicketsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentViewPagerActivity : AppCompatActivity() {
    var binding: ActivityFragmentViewPagerBinding? = null

    // tab titles
    private val titles = arrayOf("Movies", "Events", "Tickets")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentViewPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        // removing toolbar elevation
        supportActionBar!!.elevation = 0f
        binding?.viewPager?.adapter = ViewPagerFragmentAdapter(this)

        // attaching tab mediator
        binding?.let { TabLayoutMediator(binding!!.tabLayout, it.viewPager) {
                tab: TabLayout.Tab, position: Int ->
                tab.text = titles[position]
            }.attach()
        }

    }

    // adapter for the viewpager
    private inner class ViewPagerFragmentAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return MoviesFragment()
                1 -> return EventsFragment()
                2 -> return TicketsFragment()
            }
            return MoviesFragment()
        }

        override fun getItemCount(): Int {
            return titles.size
        }
    }
}