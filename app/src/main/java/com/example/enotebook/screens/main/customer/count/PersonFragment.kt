package com.example.enotebook.screens.main.customer.count

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.enotebook.data.model.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.PersonFragmentBinding
import com.example.enotebook.extentions.BaseFragment
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class PersonFragment:BaseFragment(R.layout.person_fragment) {

    private lateinit var binding: PersonFragmentBinding
    private lateinit var navController: NavController
    private val safeArgs: PersonFragmentArgs by navArgs()
    private val adapter=AdapterPersonFragment()
    private val viewModel:PersonViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        val _binding= PersonFragmentBinding.bind(view)
        binding=_binding
        binding.recyclerViewCount.adapter=adapter
        binding.tvTitle.text=safeArgs.name
        binding.btnBack.onClick {
            navController.popBackStack()
        }
        setUpObservers()
        viewModel.getPerson(safeArgs.name)
        adapter.onClickListenerButton { customer: Customer, position: Int ->
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Tolendi")
            dialog.setMessage("Bul kontakt belgilengen summani toledima?")
            dialog.setPositiveButton("AWA") { _, _ ->
                customer.id=safeArgs.id
                viewModel.deleteContact(customer,safeArgs.total)
                adapter.delete(position)
                adapter.notifyDataSetChanged()
                if (adapter.models.isEmpty()){
                    navController.popBackStack()
                }
            }
            dialog.setNegativeButton("YAQ") { _, _ ->
            }
            dialog.show()
        }
    }

    private fun setUpObservers() {
        viewModel.listPerson.observe(viewLifecycleOwner,{collection->
            when(collection.status){
                ResourceState.LOADING->binding.progressBar.visibility=View.VISIBLE
                ResourceState.SUCCESS->{
                    binding.progressBar.visibility=View.GONE
                    collection.data?.let {
                        if (it.isNotEmpty()) {
                            adapter.models = it as MutableList<Customer?>
                        }
                    }
                }
                ResourceState.ERROR->{
                    binding.progressBar.visibility=View.GONE
                    toastLN(collection.message)
                }
            }
        })
    }
}