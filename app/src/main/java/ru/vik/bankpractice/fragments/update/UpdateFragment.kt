package ru.vik.bankpractice.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.vik.bankpractice.R
import ru.vik.bankpractice.databinding.FragmentUpdateBinding
import ru.vik.bankpractice.utils.InputChecker
import ru.vik.bankpractice.viewmodel.PersonViewModel
import java.util.*
import ru.vik.bankpractice.model.Person

class UpdateFragment : Fragment() {

    var _binding: FragmentUpdateBinding? = null
    private val myBindClass get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var personViewModel: PersonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)

        personViewModel = ViewModelProvider(this)[PersonViewModel::class.java]

        // Установка значений, переданных из фрагмента ListFragment
        myBindClass.personFirstNameUpdate.setText(args.currentPerson.firstName)
        myBindClass.personLastNameUpdate.setText(args.currentPerson.lastName)
        if(args.currentPerson.sex == "М"){
            myBindClass.personSexUpdate.check(R.id.personSexUpdateMan)
        }
        else{
            myBindClass.personSexUpdate.check(R.id.personSexUpdateWoman)
        }
        myBindClass.buttonDatePickerUpdate.text = args.currentPerson.birthDate

        // Создание слушателя нажатия на кнопку "Обновить"
        myBindClass.buttonUpdatePerson.setOnClickListener {
            updatePersonInDatabase()
        }

        // Инициализация кнопки меню "Удалить"
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        deletePerson()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return myBindClass.root
    }

    // Метод удаления пользователя из таблицы
    private fun deletePerson() {

        // Вызов билдера диалогового окна
        val builder = AlertDialog.Builder(requireContext())

        // Создаём слушатель кнопки "Да"
        builder.setPositiveButton("Да"){_,_ ->

            // Вызываем метод удаления пользователя из вьюмодели, выводим тост сообщение и вызываем навигацию на фрагмент списка
            personViewModel.deletePerson(args.currentPerson)
            Toast.makeText(requireContext(), "Пользователь успешно удалён", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Нет"){_,_ -> }

        // Устанавливаем тайтл, сообщение и выводим диалоговое окно
        builder.setTitle("Удалить ${args.currentPerson.firstName} ${args.currentPerson.lastName}?")
        builder.setMessage("Вы действительно хотите удалить пользователя?")
        builder.create().show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация выбора дат
        initDatePicker()

        // Вешание слушателя нажатия на кнопку выбора даты
        myBindClass.buttonDatePickerUpdate.setOnClickListener {
            openDatePickerDialog()
        }
    }

    // Функция обновления данных в таблице Person
    private fun updatePersonInDatabase(){

        val checker = InputChecker()

        // Проверка на корректность ввода данных
        if (!checker.checkInput(
                myBindClass.personFirstNameUpdate,
                myBindClass.personLastNameUpdate,
                myBindClass.personSexUpdateMan,
                myBindClass.personSexUpdateWoman,
                myBindClass.buttonDatePickerUpdate,
                context))
        { return }

        // Взятие данных из EditText и RadioButton, при необходимости делаем первую букву строчной
        val firstName = myBindClass.personFirstNameUpdate.text.toString().replaceFirstChar { it.uppercaseChar() }
        val lastName = myBindClass.personLastNameUpdate.text.toString().replaceFirstChar { it.uppercaseChar() }
        val birthDate = myBindClass.buttonDatePickerUpdate.text.toString()
        val sex = if(myBindClass.personSexUpdateMan.isChecked){ "М" } else { "Ж" }

        // Создание объекта класса Person с обновлёнными данными
        val updatedPerson = Person(args.currentPerson.id, firstName, lastName, birthDate, sex)

        // Вызов метода updatePerson из вьюмодели
        personViewModel.updatePerson(updatedPerson)

        // Отображение сообщения об успешном добавлении и навигация на список пользователей
        Toast.makeText(context, "Успешно обновлено", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    // Метод инициализации выборщика дат
    private fun initDatePicker() {
        val dateSetListener = OnDateSetListener { _, year, month, day ->
            val mMonth = if(month+1 <= 9) {"0${month+1}"}
            else {month.toString()}

            val mDay =  if(day <= 9) {"0${day}"}
            else {day.toString()}

            val date = "$mDay/$mMonth/$year"
            myBindClass.buttonDatePickerUpdate.text = date
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

    // Удаляем binding во избежания утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}