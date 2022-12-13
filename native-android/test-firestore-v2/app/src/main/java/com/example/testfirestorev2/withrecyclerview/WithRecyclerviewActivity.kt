package com.example.testfirestorev2.withrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.R
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

// the important part here are the recyclerview class and the adapter class
//      not the way db fetching is done

// https://www.youtube.com/watch?v=Ly0xwWlUpVM

class WithRecyclerviewActivity : AppCompatActivity() {

    private val TAG = "WithRecyclerview TAG"
    private lateinit var db: FirebaseFirestore
    private lateinit var profilesRecycler: RecyclerView
    private lateinit var userList: MutableList<UserProfile>
    private lateinit var profileRecyclerAdapter: ProfileRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_recyclerview)

        bindUIWidgets()
        setUpRecyclerView()
        dbEventChangeListener()
    }

    // fetch the data from FireStore
    private fun dbEventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Profiles Recycler").document("Users")
            .collection("Users")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null) {
                        Log.d(TAG, "Firestore ERROR: ${error.message.toString()}", error)
                        return
                    }
                    // loop through the documents
                    for (doc : DocumentChange in value?.documentChanges!!) {
                        if (doc.type == DocumentChange.Type.ADDED) {
                            userList.add(doc.document.toObject(UserProfile::class.java ))
                        }
                    }
                    profileRecyclerAdapter.notifyDataSetChanged()
                }

            })
    }

    private fun setUpRecyclerView() {
        profilesRecycler.layoutManager = LinearLayoutManager(this)
        userList = mutableListOf()
        profileRecyclerAdapter = ProfileRecyclerAdapter(userList)
        profilesRecycler.adapter = profileRecyclerAdapter
    }

    private fun bindUIWidgets() {
        profilesRecycler = findViewById(R.id.profiles_recycler)
    }
}
