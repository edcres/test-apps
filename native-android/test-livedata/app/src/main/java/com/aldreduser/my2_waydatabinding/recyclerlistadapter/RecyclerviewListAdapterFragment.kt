package com.aldreduser.my2_waydatabinding.recyclerlistadapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldreduser.my2_waydatabinding.R
import com.aldreduser.my2_waydatabinding.databinding.FragmentRecyclerviewListAdapterBinding
import com.aldreduser.my2_waydatabinding.mvvmobserver.Recipe

class RecyclerviewListAdapterFragment : Fragment() {

    private var binding: FragmentRecyclerviewListAdapterBinding? = null
    private val recyclerAdapter = TestListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentRecyclerviewListAdapterBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerWithListAdapter.adapter = recyclerAdapter
            recyclerWithListAdapter.layoutManager = LinearLayoutManager(context)
        }

        recyclerAdapter.submitList(makeRecipeList())
    }

    private fun makeRecipeList(): MutableList<Recipe> {
        val fakeRecipeList = mutableListOf<Recipe>()

        val recipeNames = listOf("Beans", "Tofu", "Smoothie", "Oatmeal")
        val recipesAges = listOf(23, 45, 35, 65)
        for(i in 1..recipeNames.size) {
            val recipeInstance = Recipe(recipeNames[i - 1], recipesAges[i - 1])
            fakeRecipeList.add(recipeInstance)
        }

        Log.d("ListAdapterTAG", "makeRecipeList size: ${fakeRecipeList.size}")
        return fakeRecipeList
    }
}