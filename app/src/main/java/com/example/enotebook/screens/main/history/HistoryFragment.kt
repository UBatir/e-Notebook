package com.example.enotebook.screens.main.history

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentHistoryBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryFragment:BaseFragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var navController: NavController
    private val viewModel:HistoryViewModel by viewModel()
    private val safeArgs: HistoryFragmentArgs by navArgs()
    private val adapter=HistoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentHistoryBinding.bind(view)
        binding=_binding
        binding.recyclerViewListName.adapter=adapter
        binding.tvTitle.text=safeArgs.name
        binding.btnBack.onClick {
            navController.popBackStack()
        }
        setUpObservers()
        viewModel.getHistory(safeArgs.id)
    }

    private fun setUpObservers() {
        viewModel.listHistory.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ResourceState.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.models = it.data!!
                }
                ResourceState.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    toastLN(it.message)
                }
            }
        })
    }
}