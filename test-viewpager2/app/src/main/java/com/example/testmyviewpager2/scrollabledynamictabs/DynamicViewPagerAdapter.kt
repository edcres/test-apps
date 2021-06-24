package com.example.testmyviewpager2.scrollabledynamictabs

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testmyviewpager2.*

class DynamicViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val theActivity = DynamicViewPagerActivity()

    override fun createFragment(position: Int): Fragment {
        // Used this to change the text inside each fragment
        return DynamicFragment.getInstance(titles.size-1)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun getItemId(position: Int): Long {
        return titlesOrdinals[titles[position]]!!.toLong()
    }

    // called when a tab is removed
    override fun containsItem(itemId: Long): Boolean {
        var thisTitle = "No Title"
        titlesOrdinals.forEach{ (k, v) ->
            if(v == itemId.toInt()) {
                thisTitle = k
            }
        }
        return titles.contains(thisTitle)
    }

    fun addTab(ordinal: Int, title: String) {
        titles.add(title)

        // don't rewrite an ordinal
        if(!titlesOrdinals.containsKey(title)) {
            titlesOrdinals[title] = ordinal
        }
        notifyDataSetChanged()
        Log.d("${MY_LOG}created", "\t\t\t $titles")
        Log.d("${MY_LOG}created", "\t\t\t $titlesOrdinals")
    }

    fun removeTab(name: String) {
        titles.remove(name)
        notifyDataSetChanged()
        Log.d("${MY_LOG}removeTab", "----------------")
    }

    fun removeTab(index: Int) {
        titles.removeAt(index)
        notifyDataSetChanged()
    }
}
