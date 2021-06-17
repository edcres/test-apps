package com.example.testmyviewpager2.scrollabledynamictabs

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testmyviewpager2.titles

class DynamicViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val titleId: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return DynamicFragment.getInstance()
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun getItemId(position: Int): Long {
        // If the tabs would change position, the position of the enum class class would need to be returned.
        // But in my case I only have one fragment, and it's fine if the tabs are always in the same position.
        // todo: maybe I do need this for removing tabs
        return position.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return titles.contains(titles[itemId.toInt()])
    }

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
