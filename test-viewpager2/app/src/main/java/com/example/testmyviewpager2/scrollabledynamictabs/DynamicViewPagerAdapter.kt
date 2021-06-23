package com.example.testmyviewpager2.scrollabledynamictabs

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.testmyviewpager2.MY_LOG
import com.example.testmyviewpager2.pairsToList
import com.example.testmyviewpager2.titles
import com.example.testmyviewpager2.titlesList

class DynamicViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        // Used this to change the text inside each fragment
        return DynamicFragment.getInstance(titlesList.size-1)
//        return DynamicFragment.getInstance(titles.size-1)
    }

    override fun getItemCount(): Int {
        return titlesList.size
    }

    override fun getItemId(position: Int): Long {
        Log.d("MY_LOG", "getItemId position: $position ${titlesList.indexOf(titlesList[position]).toLong()}")
        val (theTitle, theId) = titlesList[position]
        return theId.toLong()
        //todo: use titlesList to return
//        return position.toLong()
    }

    // called when a tab is removed
    override fun containsItem(itemId: Long): Boolean {
        // itemId = id of the tab removed (0,1,2,3,4)
        /*
        if the itemId of the tab removed, matches the id of the fragment (use the text as an id)
        remove the fragment
         */
//        Log.d("${MY_LOG}itemid", "\t itemid: $itemId")

        Log.d("MY_LOG", "containsItemtrythis: $titlesList ${itemId.toInt()-1}")
        if(itemId.toInt() == 0) {itemId+1}
        val (thisTitle, _) = titlesList[itemId.toInt()-1]
//        val thisTitle = titles[itemId.toInt()-1]
        val containsTitle = pairsToList(titlesList).contains(thisTitle)
//        val containsTitle = titles.contains(thisTitle)
////        Log.d(TAG, "containsItem: ")
//        Log.d("MY_LOG", "\t\t-----containsItem called-----  $containsTitle")
        return containsTitle         // todo: maybe bug here
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
        val removed = titles.remove(name)
        Log.d("MY_LOG", "----------------")

        //todo: destroy the fragment here
        val thisFragment = DynamicFragment.getInstance() // delete parameter
        //val fragmentManager: FragmentManager = thisFragment.parentFragmentManager //requireActivity().supportFragmentManager
        //fragmentManager.beginTransaction().remove(thisFragment).commit() //.beginTransaction().remove(thisFragment).commit()


        //mFragmentManager.beginTransaction().remove(fragment).commitNow();

            //remove, hide, detach


        notifyDataSetChanged()
    }

    fun removeTab(index: Int) {
        titles.removeAt(index)
        notifyDataSetChanged()
    }
}

// If the tabs would change position, the position of the enum class class would need to be returned.
// But in my case I only have one fragment, and it's fine if the tabs are always in the same position.
// maybe I do need this for removing tabs