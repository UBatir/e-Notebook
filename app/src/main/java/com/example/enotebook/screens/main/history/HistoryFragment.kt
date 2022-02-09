package com.example.enotebook.screens.main.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentHistoryBinding
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.scope
import com.example.enotebook.extentions.toastLN
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryFragment: Fragment(R.layout.fragment_history) {

    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel by viewModel<HistoryViewModel>()
    private val safeArgs: HistoryFragmentArgs by navArgs()
    private val adapter:HistoryAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope{
        super.onViewCreated(view, savedInstanceState)
        recyclerViewListName.adapter=adapter
        tvTitle.text=safeArgs.name
        btnBack.onClick {
            findNavController().popBackStack()
        }
        setUpObservers()
        viewModel.getHistory(safeArgs.id)
    }

    private fun setUpObservers() = binding.scope {
        viewModel.listHistory.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> progressBar.visibility = View.VISIBLE
                ResourceState.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    adapter.models = it.data!!
                }
                ResourceState.ERROR -> {
                    progressBar.visibility = View.GONE
                    toastLN(it.message)
                }
            }
        })
    }
}