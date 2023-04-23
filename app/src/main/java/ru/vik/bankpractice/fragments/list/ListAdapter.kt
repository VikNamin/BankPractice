package ru.vik.bankpractice.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vik.bankpractice.data.person.Person
import ru.vik.bankpractice.databinding.PersonRowBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.PersonViewHolder>() {

    private var personList = emptyList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = PersonRowBinding.inflate(inflater, parent, false)
        return PersonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]
        with(holder.itemBinding){
            textViewFirstNamePerson.text = person.firstName
            textViewLastNamePerson.text = person.lastName
            textViewBirthDate.text = person.birthDate
            textViewSex.text = person.sex
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class PersonViewHolder(val itemBinding: PersonRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    fun setData(person: List<Person>){
        this.personList = person
        notifyDataSetChanged()
    }
}