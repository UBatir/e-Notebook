package com.example.enotebook.screens.main.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentListNameBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class ListNameFragment : BaseFragment(R.layout.fragment_list_name) {

    private lateinit var binding: FragmentListNameBinding
    private lateinit var navController: NavController
    private val viewModel: ListNameViewModel by viewModel()
    private val adapter=ListNameAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding= FragmentListNameBinding.bind(view)
        binding=_binding
        setUpObservers()
        viewModel.getContacts()
        binding.recyclerView.adapter=adapter
        binding.etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        if(it.isEmpty()){
                            viewModel.getContacts()
                        }else{
                            filter(it.toString())
                        }
                    }
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

    }

    private fun setUpObservers() {
        viewModel.listContacts.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    if (it.data!!.isNotEmpty()) {
                        adapter.models = it.data
                    }
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                }
            }
        })
    }

    fun filter(text: String) {
        val filteredListName: ArrayList<Customer> = ArrayList()
        for (eachName in adapter.models) {
            if (eachName!!.name.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))) {
                filteredListName.add(eachName)
            }
        }
        adapter.filterList(filteredListName)
    }

}