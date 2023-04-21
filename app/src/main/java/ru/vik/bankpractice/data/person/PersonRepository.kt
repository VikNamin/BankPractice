package ru.vik.bankpractice.data.person

import androidx.lifecycle.LiveData

class PersonRepository(private val personDao: PersonDao) {

    val readAllData: LiveData<List<Person>> = personDao.readAllData()

    suspend fun insertPerson(person: Person){
        personDao.insertPerson(person)
    }

}