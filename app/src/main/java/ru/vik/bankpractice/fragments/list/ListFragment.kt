package ru.vik.bankpractice.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.vik.bankpractice.R
import ru.vik.bankpractice.databinding.FragmentListBinding

class  ListFragment : Fragment() {

    var _binding: FragmentListBinding? = null
    private val myBindClass get() = _binding!!

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_list, container, false)
//        return view
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= FragmentListBinding.inflate(inflater,container,false)
        return myBindClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myBindClass.floatingActionButtonAddPerson.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }
}