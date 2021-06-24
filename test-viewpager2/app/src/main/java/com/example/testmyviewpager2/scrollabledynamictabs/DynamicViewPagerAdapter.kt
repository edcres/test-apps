package com.example.testmyviewpager2.scrollabledynamictabs

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testmyviewpager2.*
import java.lang.NumberFormatException

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
//        Log.d("${MY_LOG}getItemId", "\t getItemId position: $position. id: ${titlesOrdinals[titles[position]]}")
        return titlesOrdinals[titles[position]]!!.toLong()
//        return position.toLong()
    }

    // called when a tab is removed
    override fun containsItem(itemId: Long): Boolean {
        //Log.d("${MY_LOG}containsItem", "\t $titles \t\t itemid: $itemId")

        var thisTitle = "No Title"
        titlesOrdinals.forEach{ (k, v) ->
            if(v == itemId.toInt()) {
                thisTitle = k
            }
        }

//        val thisTitle = titles[itemId.toInt()]

//        val thisTitle = try {
//            titles[itemId.toInt() - 1]
//        } catch (e: IndexOutOfBoundsException) {
//            titles[itemId.toInt() - 2]
//        }
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
        // try deleting the ordinal
        notifyDataSetChanged()
        Log.d("${MY_LOG}removeTab", "----------------")
    }

    fun removeTab(index: Int) {
        titles.removeAt(index)
        notifyDataSetChanged()
    }
}
