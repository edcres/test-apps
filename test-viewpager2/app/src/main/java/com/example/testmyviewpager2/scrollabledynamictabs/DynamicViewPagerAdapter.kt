package com.example.testmyviewpager2.scrollabledynamictabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testmyviewpager2.titles

class DynamicViewPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity){

    private val viewPagerActivity = fragmentActivity

    override fun createFragment(position: Int): Fragment {
        return DynamicFragment()
    }

    override fun getItemCount(): Int {
        return titles.size
    }

//    override fun getItemId(position: Int): Long {
//        return super.getItemId(position)
//    }

//    override fun containsItem(itemId: Long): Boolean {
//        return super.containsItem(itemId)
//    }

    fun addTab(title: String) {
        titles.add(title)
        notifyDataSetChanged()
    }

    fun addTab(index: Int, title: String) {
        titles.add(index, title)
        notifyDataSetChanged()
    }

    fun removeTab(name: String) {
        titles.remove(name)
        notifyDataSetChanged()
    }

    fun removeTab(index: Int) {
        titles.removeAt(index)
        notifyDataSetChanged()
    }
}
