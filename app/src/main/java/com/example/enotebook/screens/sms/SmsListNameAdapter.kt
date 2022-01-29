package com.example.enotebook.screens.sms

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.data.model.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.NoteSmsListNameBinding
import com.example.enotebook.extentions.inflate
import com.example.enotebook.extentions.onClick

class SmsListNameAdapter:RecyclerView.Adapter<SmsListNameAdapter.SmsListNameViewHolder>() {

    inner class SmsListNameViewHolder(private val binding: NoteSmsListNameBinding):RecyclerView.ViewHolder(binding.root){
        fun populateModel(model: Customer){
            binding.apply{
                tvNameSms.text=model.name
                tvSum.text=model.sum.toString()
                tvNumber.text=model.phoneNumber
                tvComment.text=model.comment
                tvGetDate.text=model.setDate
                checkbox.isChecked = model.isChecked
                checkbox.onClick {
                        model.isChecked=!model.isChecked
                }
            }
        }
    }

    var models:List<Customer> = listOf()
    set(value){
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsListNameViewHolder {
        val itemView=parent.inflate(R.layout.note_sms_list_name)
        val binding=NoteSmsListNameBinding.bind(itemView)
        return SmsListNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmsListNameViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount()=models.size
}