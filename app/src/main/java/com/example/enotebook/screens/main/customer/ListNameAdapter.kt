package com.example.enotebook.screens.main.customer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.NoteItemBinding
import com.example.enotebook.screens.extentions.BaseAdapter
import com.example.enotebook.screens.extentions.inflate

class ListNameAdapter:BaseAdapter<Customer?,ListNameAdapter.ListNameViewHolder>(R.layout.note_item) {

    inner class ListNameViewHolder(private val binding:NoteItemBinding):RecyclerView.ViewHolder(binding.root){
        fun populateModel(model:Customer?){
            binding.tvName.text=model!!.name
            binding.tvSumma.text=model.sum.toString()
            binding.tvComment.text=model.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNameViewHolder {
        val itemView=parent.inflate(R.layout.note_item)
        val binding=NoteItemBinding.bind(itemView)
        return ListNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListNameViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}