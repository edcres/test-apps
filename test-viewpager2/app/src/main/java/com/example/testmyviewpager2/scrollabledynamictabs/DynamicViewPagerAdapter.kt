package com.example.testmyviewpager2.scrollabledynamictabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DynamicViewPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity){

    override fun createFragment(position: Int): Fragment {
        /*
            when (position) {
                0 -> return MoviesFragment()
                1 -> return EventsFragment()
                2 -> return TicketsFragment()
            }
            return MoviesFragment()
         */
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        // return titles.size   //get the titles variable from the activity
        TODO("Not yet implemented")
    }
}