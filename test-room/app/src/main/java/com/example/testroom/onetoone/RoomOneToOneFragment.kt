package com.example.testroom.onetoone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testroom.R

// https://developer.android.com/training/data-storage/room/relationships#kotlin

// A one-to-one relationship between two entities is a relationship where each instance of the
//      parent entity corresponds to exactly one instance of the child entity, and vice-versa.
// One of the entities must include a variable that is a reference to the
//      primary key of the other entity.
/**
 * There are 2 approaches to declare Room relationships.
 * - Intermediate data class (older)
 *      - Have to declare a separate Entity that represents the relationship. (ex. UserAndBook)
 * - Multimap return types (newer. Room 2.4.0 and higher)
 *      - The relationship is declared in the DAO query
 *          - @Query("SELECT * FROM user" + "JOIN book ON user.id = book.user_id" )
 *          - fun loadUserAndBookNames(): Map<User, List<Book>>
 *      - Requires queries to do more work.
 */
// Can use the @Embedded annotation inside an Entity to put another entity as a property.

class RoomOneToOneFragment : Fragment() {

    private val fragmentTAG = "OneToOneFragTAG"
    private lateinit var viewModelFactory: OneToOneViewModelFactory
    private lateinit var viewModel: OneToOneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_one_to_one, container, false)
        return view
    }
}