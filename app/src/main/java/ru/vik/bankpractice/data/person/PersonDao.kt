package ru.vik.bankpractice.data.person

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

// Интерфейс для работы с таблицей Person

@Dao
interface PersonDao {

    // Функция Upsert (Insert)
    @Upsert
    suspend fun insertPerson(person: Person)

    // Функция Delete
    @Delete
    suspend fun deletePerson(person: Person)

    // Чтение всей таблицы
    @Query("SELECT * FROM person")
    fun readAllData(): LiveData<List<Person>>

    // Функция сортировки по Фамилии (lastName)
//    @Query("SELECT * FROM person ORDER BY lastName")
//    fun getPersonsOrderByName(): Flow<List<Person>>

}