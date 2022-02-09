package com.example.enotebook.screens.sms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSmsListNameBinding
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.toastLN
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class SmsListNameFragment: Fragment(R.layout.fragment_sms_list_name) {

    private val binding by viewBinding(FragmentSmsListNameBinding::bind)
    private val viewModel by viewModel<SmsListNameViewModel>()
    private val mAdapter:SmsListNameAdapter by inject()
    var check:Boolean=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModel.getListName()
        binding.recyclerViewListName.adapter=mAdapter
        binding.btnCheckAll.onClick {
            check=!check
            if (check){
                mAdapter.models.forEach {
                    it.isChecked=true
                }
            }else{
                mAdapter.models.forEach {
                    it.isChecked=false
                }
            }
            mAdapter.notifyDataSetChanged()
        }
        binding.btnSendSms.onClick {
                viewModel.sendSms(
                    mAdapter.models.filter { it.isChecked }.map { it.name },
                    mAdapter.models.filter { it.isChecked }.map { it.phoneNumber },
                    mAdapter.models.filter { it.isChecked }.map { it.sum.toString() },
                    mAdapter.models.filter { it.isChecked }.map { getDateFromUTCTimestamp(it.createDate) },
                    requireContext()
                )
        }
    }

    private fun getDateFromUTCTimestamp(mTimestamp: Long): String {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.format(mTimestamp*1000).toString()
    }

    private fun setUpObservers() {
        viewModel.smsListName.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ResourceState.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    mAdapter.models = it.data!!

                }
                ResourceState.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    toastLN(it.message)
                }
            }
        })
    }
}