package ru.vik.bankpractice.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vik.bankpractice.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    var _binding: FragmentAddBinding? = null
    private val myBindClass get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= FragmentAddBinding.inflate(inflater,container,false)
        return myBindClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}