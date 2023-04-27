package ru.vik.bankpractice.utils

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast

class InputChecker {
    // Метод проверки корректности данных
    fun checkInput(
        firstNameEditText: EditText,
        lastNameEditText: EditText,
        radioMan: RadioButton,
        radioWoman: RadioButton,
        dateButton: Button,
        context: Context?): Boolean {
        when {
            // Если данные не пустая строка при удалении всех пробелов
            firstNameEditText.text.filter { !it.isWhitespace() }.toString() == "" -> {
                Toast.makeText(context, "Введите корректное Имя!", Toast.LENGTH_SHORT).show()
                return false
            }
            // Если данные не пустая строка при удалении всех пробелов
            lastNameEditText.text.filter { !it.isWhitespace() }.toString() == "" -> {
                Toast.makeText(context, "Введите корректную Фамилию!", Toast.LENGTH_SHORT).show()
                return false
            }
            // Если выбрано хотя бы один RadioButton из группы
            !radioMan.isChecked && !radioWoman.isChecked -> {
                Toast.makeText(context, "Обязательно выберите Пол!", Toast.LENGTH_SHORT).show()
                return false
            }
            // Если данные не пустая строка при удалении всех НЕ цифр
            dateButton.text.filter { it.isDigit() }.toString() == "" -> {
                Toast.makeText(context, "Введите корректную Дату Рождения!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        // Значит данные корректны
        return true
    }
}