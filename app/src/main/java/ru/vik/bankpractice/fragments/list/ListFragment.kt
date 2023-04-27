package ru.vik.bankpractice.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vik.bankpractice.R
import ru.vik.bankpractice.viewmodel.PersonViewModel
import ru.vik.bankpractice.databinding.FragmentListBinding

class  ListFragment : Fragment() {

    var _binding: FragmentListBinding? = null
    private val myBindClass get() = _binding!!

    private lateinit var personViewModel: PersonViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= FragmentListBinding.inflate(inflater,container,false)

        adapter = ListAdapter()

        val layoutManager = LinearLayoutManager(context)
        myBindClass.recyclerViewPerson.layoutManager = layoutManager
        myBindClass.recyclerViewPerson.adapter = adapter

        personViewModel = ViewModelProvider(this)[PersonViewModel::class.java]
        personViewModel.readAllData.observe(viewLifecycleOwner, Observer { person ->
            adapter.setData(person)
        })

        return myBindClass.root
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