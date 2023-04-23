package ru.vik.bankpractice.data.person

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vik.bankpractice.data.BankDatabase

class PersonViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Person>>
    private val repository: PersonRepository

    init {
        val personDao = BankDatabase.getDatabase(application).personDao()
        repository = PersonRepository(personDao)
        readAllData = repository.readAllData
    }

    fun insertPerson(person: Person){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPerson(person)
        }
    }

}