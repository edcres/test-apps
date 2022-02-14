package com.example.testroom.onetoone.data

import com.example.testroom.onetoone.data.entities.CarOneToOne
import com.example.testroom.onetoone.data.entities.PersonAndCarOneToOne
import com.example.testroom.onetoone.data.entities.PersonOneToOne
import kotlinx.coroutines.flow.Flow

class OneToOneRepository(private val oneToOneDao: OneToOneDao) {

    // todo: uncomment this
    val allPersonsAndCars: Flow<List<PersonAndCarOneToOne>> = oneToOneDao.getPersonsAndCars()

    val allPersons: Flow<List<PersonOneToOne>> = oneToOneDao.getPersons()

//    suspend fun getPersonsAndCarNames(): Flow<List<PersonAndCarOneToOne>> {
//        return oneToOneDao.getPersonsAndCarNames()
//    }

//    suspend fun getPersonAndCarWithPersonName(personName: String):
//            Flow<List<PersonAndCarOneToOne>> {
//        return oneToOneDao.getPersonAndCarWithPersonName(personName)
//    }

    suspend fun insertPerson(person: PersonOneToOne) {
        oneToOneDao.insertPerson(person)
    }

    suspend fun insertCar(car: CarOneToOne) {
        oneToOneDao.insertCar(car)
    }

    suspend fun deleteAll() {
        oneToOneDao.deleteAllPersons()
        oneToOneDao.deleteAllCars()
    }
}