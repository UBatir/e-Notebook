package com.example.enotebook.screens.main.customer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.MainActivity
import com.example.enotebook.R
import com.example.enotebook.data.local.SharedPreferences
import com.example.enotebook.data.model.Customer
import com.example.enotebook.databinding.FragmentListNameBinding
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.scope
import com.example.enotebook.extentions.toastLN
import com.example.enotebook.screens.main.customer.dialogs.ChangeBalanceDialog
import com.example.enotebook.screens.main.customer.dialogs.RenameDialog
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ListNameFragment : Fragment(R.layout.fragment_list_name) {

    private val binding by viewBinding(FragmentListNameBinding::bind)
    private val viewModel by viewModel<ListNameViewModel>()
    private val adapter: ListNameAdapter by inject()
    private var selectedLanguage = ""
    var allModel: MutableList<Customer> = mutableListOf()
    private val settings: SharedPreferences by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getContacts()
        setUpObservers()
        adapter.setOnClickItemOptionsListener { view: View, customer: Customer, position: Int ->
            onItemOptions(view, customer, position)
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.getContacts()
            swipeRefresh.isRefreshing = false
        }
        recyclerView.adapter = adapter
        adapter.setOnClickItemListener {
            findNavController().navigate(
                ListNameFragmentDirections.actionNameListFragmentToHistoryFragment(
                    it.id,
                    it.name
                )
            )
        }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    adapter.query=it.toString()
                    if (it.isEmpty()) {
                        adapter.models=allModel
                    } else {
                        filter(it.toString())
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionSignOut -> {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle(getString(R.string.exit_from_account))
                    dialog.setMessage(getString(R.string.message_exit))
                    dialog.setPositiveButton(getString(R.string.yes)) { _, _ ->
                        viewModel.signOut()
                        val parentNavController = Navigation.findNavController(
                            requireParentFragment().requireActivity() as MainActivity,
                            R.id.nav_host_fragment
                        )
                        parentNavController.navigate(R.id.action_mainFragment_to_authorizationFragment)
                    }
                    dialog.setNegativeButton(getString(R.string.no)) { _, _ ->
                    }
                    dialog.show()
                    return@setOnMenuItemClickListener true
                }
                R.id.actionChangeLanguage -> {
                    showDialog()
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }
    }

    private fun setUpObservers() = binding.scope {
        viewModel.listContacts.observe(viewLifecycleOwner, { collection ->
            when (collection.status) {
                ResourceState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                ResourceState.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    collection.data?.let {
                        adapter.models = it
                        allModel = it
                    }
                }
                ResourceState.ERROR -> {
                    progressBar.visibility = View.GONE
                    toastLN(collection.message)
                }
            }
        })
    }

    fun filter(text: String) {
        val filteredListName: MutableList<Customer> = mutableListOf()
        allModel.forEach {doc->
            if (doc.name.toLowerCase(Locale.getDefault())
                    .contains(text.toLowerCase(Locale.getDefault()))
            ) {
                filteredListName.add(doc)
            }
        }
        adapter.models=filteredListName
    }

    private fun onItemOptions(view: View, customer: Customer, position: Int) {
        val optionsMenu = PopupMenu(requireContext(), view)
        val menuInflater = optionsMenu.menuInflater
        menuInflater.inflate(R.menu.item_menu, optionsMenu.menu)
        optionsMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itemHistory -> {
                    findNavController().navigate(
                        ListNameFragmentDirections.actionNameListFragmentToHistoryFragment(
                            customer.id,
                            customer.name
                        )
                    )
                }
                R.id.itemChangeBalance -> {
                    val dialog = ChangeBalanceDialog(requireContext())
                    dialog.name = customer.name
                    dialog.sum = customer.sum
                    dialog.changeBalance { total: Long, sum: Long, comment: String ->
                        customer.sum = total
                        viewModel.changeBalance(customer, sum, comment)
                        adapter.notifyDataSetChanged()
                    }
                    dialog.show()
                }
                R.id.itemRename -> {
                    val dialog = RenameDialog(requireContext())
                    dialog.name = customer.name
                    dialog.renameContact { name ->
                        customer.name = name
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

    private fun showDialog() {
        val dialogLanguages = AlertDialog.Builder(requireContext())
        dialogLanguages.setTitle(getString(R.string.change_language))
        val languages = arrayOf(
            getString(R.string.russian_language),
            getString(R.string.karakalpak_language),
            getString(R.string.uzbek_language)
        )
        dialogLanguages.setSingleChoiceItems(languages, settings.position) { _, i ->
            selectedLanguage = languages[i]
            settings.position = i
        }
        dialogLanguages.setPositiveButton("Ok") { _, _ ->
            when (selectedLanguage) {
                getString(R.string.russian_language) -> {
                    settings.language = "ru"
                    setLocale()
                }
                getString(R.string.karakalpak_language) -> {
                    settings.language = "kaa"
                    setLocale()
                }
                getString(R.string.uzbek_language) -> {
                    settings.language = "uz"
                    setLocale()
                }
            }
        }
        dialogLanguages.show()
    }

    private fun setLocale() {
        val refresh = Intent(
            requireContext(),
            MainActivity::class.java
        )
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        requireActivity().intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        requireActivity().intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(refresh)
    }
}