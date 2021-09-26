package com.example.testfirestorev2.withrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.R

class ProfileRecyclerAdapter(private val userList: MutableList<UserProfile>) :
    RecyclerView.Adapter<ProfileRecyclerAdapter.ProfileRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_recycler_item, parent, false)

        return ProfileRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileRecyclerViewHolder, position: Int) {
        val user : UserProfile = userList[position]
        holder.firstName.text = user.firstName
        holder.lastName.text = user.lastName
        holder.age.text = user.age.toString()


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ProfileRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val firstName : TextView = itemView.findViewById(R.id.tv_first_name)
        val lastName : TextView = itemView.findViewById(R.id.tv_lat_name)
        val age : TextView = itemView.findViewById(R.id.tv_age )
    }
}