package com.example.enotebook.screens.main.customer

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentListNameBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class ListNameFragment : BaseFragment(R.layout.fragment_list_name) {

    private lateinit var binding: FragmentListNameBinding
    private lateinit var navController: NavController
    private val viewModel: ListNameViewModel by viewModel()
    private val adapter=ListNameAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentListNameBinding.bind(view)
        binding=_binding
        setUpObservers()
        viewModel.getContacts()
        with(binding){
            recyclerView.adapter=adapter
            fab.onClick {
                val action= ListNameFragmentDirections.actionReportFragment2ToAddContactFragment()
                navController.navigate(action)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.listContacts.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.SUCCESS->{
                    if(it.data!!.isNotEmpty()){
                        adapter.models=it.data
                    }
                }
                ResourceState.ERROR->{
                    toastLN(it.message)
                }
            }
        })
    }
}