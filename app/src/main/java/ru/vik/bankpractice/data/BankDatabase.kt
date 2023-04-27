package ru.vik.bankpractice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.vik.bankpractice.model.Person
import ru.vik.bankpractice.data.person.PersonDao

// Таблицы, входящие в БД и версия БД
@Database(
    entities = [
        Person::class
               ],
    version = 1
)
abstract class BankDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao

    // Синглтон. Создание инстанса БД
    companion object{
        @Volatile
        private var INSTANSCE: BankDatabase? = null

        fun getDatabase(context: Context): BankDatabase {
            val tempInstance = INSTANSCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BankDatabase::class.java,
                    "bank_db"
                ).build()
                INSTANSCE = instance
                return instance
            }
        }
    }

//    abstract val dao: PersonDao

}