package com.example.testroom.onetoone.data

import androidx.room.*
import com.example.testroom.onetoone.data.entities.CarOneToOne
import com.example.testroom.onetoone.data.entities.PersonAndCarOneToOne
import com.example.testroom.onetoone.data.entities.PersonOneToOne
import kotlinx.coroutines.flow.Flow

@Dao
interface OneToOneDao {

    // returns all instances of the data class that pairs the parent entity and the child entity.
    @Transaction    // Ensures that the whole operation is performed atomically.
    @Query("SELECT * FROM one_to_one_person")
//    fun getPersonsAndCars(): Flow<List<PersonAndCarOneToOne>>
    fun getPersonsAndCars(): List<PersonAndCarOneToOne>

//    // todo: bug here
//    @Query("SELECT one_to_one_person.name, one_to_one_car.name " +
//            "FROM one_to_one_person, one_to_one_car " +
//            "WHERE one_to_one_person.id = one_to_one_car.person_id")
//    fun getPersonsAndCarNames(): Flow<List<PersonAndCarOneToOne>>

//    // todo: bug here
//    @Transaction
//    @Query("SELECT * FROM one_to_one_car WHERE name = :personName")
//    fun getPersonAndCarWithPersonName(personName: String):
//            Flow<List<PersonAndCarOneToOne>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(personOneToOne: PersonOneToOne)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(carOneToOne: CarOneToOne)

    @Query("DELETE FROM one_to_one_person")
    suspend fun deleteAllPersons()

    @Query("DELETE FROM one_to_one_car")
    suspend fun deleteAllCars()

    // idk if I have to delete the relationship as well
}