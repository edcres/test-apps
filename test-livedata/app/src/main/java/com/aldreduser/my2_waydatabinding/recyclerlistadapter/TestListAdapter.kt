package com.aldreduser.my2_waydatabinding.recyclerlistadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.my2_waydatabinding.R
import com.aldreduser.my2_waydatabinding.databinding.RecipeRecyclerItemBinding
import com.aldreduser.my2_waydatabinding.mvvmobserver.Recipe

// ListAdapter allows for easy use of DiffUtil
// DiffUtil computes Diffs on background thread meaning we don’t need to implement it by ourselves.

// 'submitList(List)'   to provide the list to RecyclerView.Adapter

class TestListAdapter :
    ListAdapter<Recipe, TestListAdapter.RecipeViewHolder>(RecipeItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    // class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class RecipeViewHolder(val binding: RecipeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(recipeItem: Recipe) {
            // bind views with data
            binding.apply {
                recipeNameTxt.text = recipeItem.name
                recipeAgeTxt.text = recipeItem.age.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }

    }
}

class RecipeItemDiffCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

}
