package ru.vik.bankpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


// TODO:    Доделать реализацию добавления записей в таблицу Person, начать реализовывать другие таблицы,
// TODO:    подумать над тем как лучше всего осуществлять ввод даты, через TextWatcher или через календарь,
// TODO:    реализовать удаление, редактирование, поиск по записям, сортировку, добавить комментарии


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.mainFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}