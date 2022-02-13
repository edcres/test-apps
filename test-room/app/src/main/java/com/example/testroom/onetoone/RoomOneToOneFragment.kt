package com.example.testroom.onetoone

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.testroom.databinding.FragmentRoomOneToOneBinding
import com.example.testroom.onetoone.data.entities.CarOneToOne
import com.example.testroom.onetoone.data.entities.PersonAndCarOneToOne
import com.example.testroom.onetoone.data.entities.PersonOneToOne

// https://developer.android.com/training/data-storage/room/relationships#kotlin

// A one-to-one relationship between two entities is a relationship where each instance of the
//      parent entity corresponds to exactly one instance of the child entity, and vice-versa.
// One of the entities must include a variable that is a reference to the
//      primary key of the other entity.
/**
 * There are 2 approaches to declare Room relationships.
 * - Intermediate data class (older)
 *      - Have to declare a separate Class (not @Entity) that represents
 *          the relationship. (ex. UserAndBook)
 * - Multimap return types (newer. Room 2.4.0 and higher)
 *      - The relationship is declared in the DAO query
 *          - @Query("SELECT * FROM user" + "JOIN book ON user.id = book.user_id" )
 *          - fun loadUserAndBookNames(): Map<User, List<Book>>
 *      - Requires queries to do more work.
 */
// Can use the @Embedded annotation inside an Entity to put another entity as a property.

class RoomOneToOneFragment : Fragment() {

    private val fragmentTAG = "OneToOneFragTAG"
    private var binding: FragmentRoomOneToOneBinding? = null
    private lateinit var viewModelFactory: OneToOneViewModelFactory
    private lateinit var viewModel: OneToOneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRoomOneToOneBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        viewModelFactory = OneToOneViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[OneToOneViewModel::class.java]
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel.allPersonsAndCars.observe(viewLifecycleOwner) { personAndCar ->
                setStartDataToView(personAndCar)
            }
            deleteAllBtn.setOnClickListener{
                viewModel.deleteAll()
            }
            save1Btn.setOnClickListener {
                if(personTxt1.text.toString().isNotEmpty() && carTxt1.text.toString().isNotEmpty()) {
                    btnClicked(personTxt1.text.toString(), carTxt1.text.toString())
                    save1Btn.visibility = View.GONE
                }
            }
            save2Btn.setOnClickListener {
                if(personTxt2.text.toString().isNotEmpty() && carTxt2.text.toString().isNotEmpty()) {
                    btnClicked(personTxt2.text.toString(), carTxt2.text.toString())
                    save2Btn.visibility = View.GONE
                }
            }
            save3Btn.setOnClickListener {
                if(personTxt3.text.toString().isNotEmpty() && carTxt3.text.toString().isNotEmpty()) {
                    btnClicked(personTxt3.text.toString(), carTxt3.text.toString())
                    save3Btn.visibility = View.GONE
                }
            }
            save4Btn.setOnClickListener {
                if(personTxt4.text.toString().isNotEmpty() && carTxt4.text.toString().isNotEmpty()) {
                    btnClicked(personTxt4.text.toString(), carTxt4.text.toString())
                    save4Btn.visibility = View.GONE
                }
            }
            save5Btn.setOnClickListener {
                if(personTxt5.text.toString().isNotEmpty() && carTxt5.text.toString().isNotEmpty()) {
                    btnClicked(personTxt5.text.toString(), carTxt5.text.toString())
                    save5Btn.visibility = View.GONE
                }
            }
        }
    }

    private fun btnClicked(personName: String, carName: String) {
        val person = PersonOneToOne(name = personName)
        // idk if I can add the 'personId' just yet to the car entity
        val car = CarOneToOne(car = carName, personName = personName, personId = person.id)
        viewModel.insert(person)
        viewModel.insert(car)
    }

    private fun setStartDataToView(personAndCar: List<PersonAndCarOneToOne>) {
        binding?.apply {
            val personTexts = listOf(personTxt1, personTxt2, personTxt3, personTxt4, personTxt5)
            val carTexts = listOf(carTxt1, carTxt2, carTxt3, carTxt4, carTxt5)
            for (i in personAndCar.indices) {
                val personTxtMsg = "${personAndCar[i].person.id} ${personAndCar[i].person.name}"
                val carTxtMsg = "${personAndCar[i].car.id} ${personAndCar[i].car.car}"
                personTexts[i].setText(personTxtMsg)
                carTexts[i].setText(carTxtMsg)
                Log.d(fragmentTAG, "relation proof: ${personAndCar[i].person.name}" +
                        " ${personAndCar[i].car.personName}")
            }
        }

    }
}















