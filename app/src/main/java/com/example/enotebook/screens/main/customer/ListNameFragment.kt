package com.example.enotebook.screens.main.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.data.model.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentListNameBinding
import com.example.enotebook.extentions.BaseFragment
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.screens.main.customer.dialogs.ChangeBalanceDialog
import com.example.enotebook.screens.main.customer.dialogs.RenameDialog
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class ListNameFragment : BaseFragment(R.layout.fragment_list_name) {

    private lateinit var binding: FragmentListNameBinding
    private lateinit var navController: NavController
    private val viewModel: ListNameViewModel by viewModel()
    private val adapter: ListNameAdapter by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding= FragmentListNameBinding.bind(view)
        binding=_binding
        setUpObservers()
        viewModel.getContacts()
        adapter.setOnClickItemOptionsListener { view: View, customer: Customer, position: Int ->
            onItemOptions(view,customer,position)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getContacts()
            binding.swipeRefresh.isRefreshing=false
        }
        binding.recyclerView.adapter=adapter
        adapter.setOnClickItemListener {
            val action=ListNameFragmentDirections.actionReportFragmentToPersonFragment(it.id,it.name,it.sum)
            navController.navigate(action)
        }
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
        binding.ivSettings.onClick {
            val action=ListNameFragmentDirections.actionReportFragmentToSettingsFragment()
            navController.navigate(action)
        }

    }

    private fun setUpObservers() {
        viewModel.listContacts.observe(viewLifecycleOwner, { collection ->
            when (collection.status) {
                ResourceState.LOADING->{
                    binding.progressBar.visibility=View.VISIBLE
                }
                ResourceState.SUCCESS -> {
                    binding.progressBar.visibility=View.GONE
                    collection.data?.let {
                        if (it.isNotEmpty()) {
                            adapter.models = it as MutableList<Customer?>
                        }
                    }
                }
                ResourceState.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    toastLN(collection.message)
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
        adapter.filterList(filteredListName as MutableList<Customer?>)
    }


    private fun onItemOptions(view: View, customer: Customer, position:Int){
        val optionsMenu= PopupMenu(requireContext(),view)
        val menuInflater=optionsMenu.menuInflater
        menuInflater.inflate(R.menu.item_menu,optionsMenu.menu)
        optionsMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.itemHistory->{
                    val action=ListNameFragmentDirections.actionReportFragmentToHistoryFragment(customer.id,customer.name)
                    navController.navigate(action)
                }
                R.id.itemChangeBalance->{
                    val dialog= ChangeBalanceDialog(requireContext())
                    dialog.name=customer.name
                    dialog.sum=customer.sum
                    dialog.changeBalance { total: Long, sum: Long, comment:String ->
                        customer.sum=total
                        viewModel.changeBalance(customer,sum,comment)
                        adapter.notifyDataSetChanged()
                    }
                    dialog.show()
                }
                R.id.itemRename->{
                    val dialog= RenameDialog(requireContext())
                    dialog.name = customer.name
                    dialog.renameContact {name->
                        customer.name=name
                        viewModel.changeName(customer)
                        adapter.notifyDataSetChanged()
                    }
                    dialog.show()
                }
                R.id.itemDelete -> {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Kontakti oshiriw")
                    dialog.setMessage("Siz bul kontakti oshirejaqsizba?")
                    dialog.setPositiveButton("AWA") { _, _ ->
                        viewModel.deleteContact(customer)
                        adapter.delete(position)
                    }
                    dialog.setNegativeButton("YAQ") { _, _ ->
                    }
                    dialog.show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        optionsMenu.show()
    }
}