package ru.vik.bankpractice.data.person

import androidx.room.Entity
import androidx.room.PrimaryKey

// Data класс объектов типа Клиент
// название таблицы -> person
@Entity(tableName = "person")
data class Person(

    // Первичный ключ - ID, автогенерация ID библиотекой Room включена
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Столбцы: Имя, фамилия, дата рождения, пол
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val sex: String,
)
