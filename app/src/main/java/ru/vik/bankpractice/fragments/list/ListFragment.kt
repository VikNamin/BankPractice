package ru.vik.bankpractice.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vik.bankpractice.R
import ru.vik.bankpractice.databinding.FragmentListBinding
import ru.vik.bankpractice.model.Person
import ru.vik.bankpractice.viewmodel.PersonViewModel
import java.util.*

class  ListFragment : Fragment() {

    var _binding: FragmentListBinding? = null
    private val myBindClass get() = _binding!!

    private lateinit var personViewModel: PersonViewModel
    private lateinit var adapter: ListAdapter

    private var personList = emptyList<Person>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= FragmentListBinding.inflate(inflater,container,false)

        adapter = ListAdapter()

        val layoutManager = LinearLayoutManager(context)
        myBindClass.recyclerViewPerson.layoutManager = layoutManager
        myBindClass.recyclerViewPerson.adapter = adapter

        personViewModel = ViewModelProvider(this)[PersonViewModel::class.java]
        personViewModel.readAllData.observe(viewLifecycleOwner, Observer { person ->
            personList = person
            adapter.setData(person)
        })

        // Инициализация кнопки меню "Поиск"
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        filterPerson(newText)
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_search -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return myBindClass.root
    }

    // Метод сортировки массива пользователей
    private fun filterPerson(newText: String) {
        val filteredList: ArrayList<Person> = ArrayList<Person>()

        for (person in personList){
            if(person.lastName.lowercase().contains(newText.lowercase())){
                filteredList.add(person)
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(requireContext(), "Не найдено", Toast.LENGTH_SHORT).show()
        }
        else{
            adapter.setData(filteredList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myBindClass.floatingActionButtonAddPerson.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    // Удаляем binding во избежания утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}