package com.example.testmyviewpager2.scrollabledynamictabs

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testmyviewpager2.*

class DynamicViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        // Used this to change the text inside each fragment
        return DynamicFragment.getInstance(titles.size-1)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun getItemId(position: Int): Long {
        Log.d("MY_LOG", "getItemId position: $position ${titlesOrdinals[titles[position]]}")
        return titlesOrdinals[titles[position]]!!.toLong()
//        return position.toLong()
    }

    // called when a tab is removed
    override fun containsItem(itemId: Long): Boolean {
        Log.d("${MY_LOG}itemid", "\t $titles \t\t itemid: $itemId")
        // todo: maybe bug here (with the +1 or -1)
        if (itemId.toInt() == 0) { itemId + 1 }
        val thisTitle = titles[itemId.toInt() - 1]
        return titles.contains(thisTitle)
    }

    fun addTab(ordinal: Int, title: String) {
        titles.add(title)
        titlesOrdinals[title] = ordinal
        notifyDataSetChanged()
    }

    fun removeTab(name: String) {
        titles.remove(name)
        notifyDataSetChanged()
        Log.d("MY_LOG", "----------------")
    }

    fun removeTab(index: Int) {
        titles.removeAt(index)
        notifyDataSetChanged()
    }
}
