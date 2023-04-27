package ru.vik.bankpractice.repository

import androidx.lifecycle.LiveData
import ru.vik.bankpractice.data.person.PersonDao
import ru.vik.bankpractice.model.Person

class PersonRepository(private val personDao: PersonDao) {

    val readAllData: LiveData<List<Person>> = personDao.readAllData()

    suspend fun insertPerson(person: Person){
        personDao.insertPerson(person)
    }

    suspend fun updatePerson(person: Person){
        personDao.updatePerson(person)
    }

}