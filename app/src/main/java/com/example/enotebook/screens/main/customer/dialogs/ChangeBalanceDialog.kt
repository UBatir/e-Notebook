package com.example.enotebook.screens.main.customer.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.enotebook.databinding.DiaogChangeBalanceBinding
import com.example.enotebook.screens.extentions.onClick
import java.util.*

class ChangeBalanceDialog(context: Context):Dialog(context) {

    private lateinit var binding:DiaogChangeBalanceBinding
    var name:String=""
    var sum:Long=0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val _binding=DiaogChangeBalanceBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        binding=_binding
        binding.tvName.text=name
        binding.tvSum.text=sum.toString()
        binding.btnPlus.onClick {
            body.invoke(sum+binding.etSum.text.toString().toLong(),binding.etSum.text.toString().toLong(),binding.etComment.text.toString())
            dismiss()
        }
        binding.btnMinus.onClick {
            body.invoke(sum-binding.etSum.text.toString().toLong(),-binding.etSum.text.toString().toLong(),binding.etComment.text.toString())
            dismiss()
        }
    }

    var body:(total:Long,sum:Long,comment:String)->Unit= { total: Long, sum: Long, comment:String -> }
    fun changeBalance(body: (total: Long,sum:Long,comment:String) -> Unit){
        this.body=body
    }

}
