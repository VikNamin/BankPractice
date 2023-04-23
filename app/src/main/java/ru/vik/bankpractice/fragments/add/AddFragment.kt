package ru.vik.bankpractice.fragments.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.vik.bankpractice.R
import ru.vik.bankpractice.data.person.Person
import ru.vik.bankpractice.data.person.PersonViewModel
import ru.vik.bankpractice.databinding.FragmentAddBinding
import java.util.*


class AddFragment : Fragment() {

    var _binding: FragmentAddBinding? = null
    private val myBindClass get() = _binding!!

    private lateinit var personViewModel: PersonViewModel

    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= FragmentAddBinding.inflate(inflater,container,false)
        return myBindClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создание объекта вьюмодели
        personViewModel = ViewModelProvider(this)[PersonViewModel::class.java]

        // Инициализация выбора дат
        initDatePicker()

        // Вешание слушателя нажатия на кнопку выбора даты
        myBindClass.buttonDatePicker.setOnClickListener {
            openDatePickerDialog()
        }

        // Вешание слушателя нажатия на кнопку добавление пользователя
        myBindClass.buttonAddPerson.setOnClickListener {
            insertPersonToDatabase()
        }
    }

    // Метод добавление пользователя в БД
    private fun insertPersonToDatabase() {

        // Проверка на корректность ввода данных
        if (!checkInput()){
            return
        }

        // Взятие данных из EditText и RadioButton, при необходимости делаем первую букву строчной
        val firstName = myBindClass.editTextPersonFirstName.text.toString().replaceFirstChar { it.uppercaseChar() }
        val lastName = myBindClass.editTextPersonLastName.text.toString().replaceFirstChar { it.uppercaseChar() }
        val birthDate = myBindClass.buttonDatePicker.text.toString()
        val sex = if(myBindClass.radioMan.isChecked){ "М" } else { "Ж" }

        Log.d("VikLog", "Имя: $firstName, Фамилия: $lastName, Д.Р: $birthDate, Пол: $sex")

        // Создание объекта класса Person с вводом соответствующих полей
        val person = Person(0, firstName, lastName, birthDate, sex)

        // Вызов метода insertPerson во вьюмодели
        personViewModel.insertPerson(person)
        Toast.makeText(context, "Успешно добавлено", Toast.LENGTH_SHORT).show()

        // Возвращение к списку пользователей при успешном добавлении пользователя
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    // Метод проверки корректности данных
    private fun checkInput(): Boolean {
        when {
            // Если данные не пустая строка при удалении всех пробелов
            myBindClass.editTextPersonFirstName.text.filter { !it.isWhitespace() }.toString() == "" -> {
                Toast.makeText(context, "Введите корректное Имя!", Toast.LENGTH_SHORT).show()
                return false
            }
            // Если данные не пустая строка при удалении всех пробелов
            myBindClass.editTextPersonLastName.text.filter { !it.isWhitespace() }.toString() == "" -> {
                Toast.makeText(context, "Введите корректную Фамилию!", Toast.LENGTH_SHORT).show()
                return false
            }
            // Если выбрано хотя бы один RadioButton из группы
            !myBindClass.radioMan.isChecked && !myBindClass.radioWoman.isChecked -> {
                Toast.makeText(context, "Обязательно выберите Пол!", Toast.LENGTH_SHORT).show()
                return false
            }
            // Если данные не пустая строка при удалении всех НЕ цифр
            myBindClass.buttonDatePicker.text.filter { it.isDigit() }.toString() == "" -> {
                Toast.makeText(context, "Введите корректную Дату Рождения!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        // Значит данные корректны
        return true
    }

    // Метод инициализации выборщика дат
    private fun initDatePicker() {
        val dateSetListener = OnDateSetListener { _, year, month, day ->
            val mMonth = if(month+1 <= 9) {"0${month+1}"}
            else {month.toString()}

            val mDay =  if(day <= 9) {"0${day}"}
            else {day.toString()}

            val date = "$mDay/$mMonth/$year"
            myBindClass.buttonDatePicker.text = date
        }

        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(requireContext(), dateSetListener, year, month, day)
    }

    // Метод показа диалогового окна с календарём
    private fun openDatePickerDialog() {
        datePickerDialog.show()
    }
}