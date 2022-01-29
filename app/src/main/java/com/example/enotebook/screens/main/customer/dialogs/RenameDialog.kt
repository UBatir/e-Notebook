package com.example.enotebook.screens.main.customer.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.enotebook.databinding.DiaogRenameBinding
import com.example.enotebook.extentions.onClick

class RenameDialog(context:Context):Dialog(context) {

    private lateinit var binding:DiaogRenameBinding
    var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val _binding=DiaogRenameBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        binding=_binding
        with(binding){
            tvName.text=name
            etRename.setText(name)
            btnOk.onClick {
                body.invoke(etRename.text.toString())
                dismiss()
            }
            btnCancel.onClick {
                dismiss()
            }
        }
    }

    var body:(name:String)->Unit={}
    fun renameContact(body:(name:String)->Unit){
        this.body=body
    }
}