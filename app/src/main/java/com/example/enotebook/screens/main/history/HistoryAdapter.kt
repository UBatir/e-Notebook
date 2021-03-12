package com.example.enotebook.screens.main.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.NoteHistoryBinding
import com.example.enotebook.screens.extentions.inflate

class HistoryAdapter:RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding:NoteHistoryBinding):RecyclerView.ViewHolder(binding.root){
        fun populateModel(model:Customer?){
            binding.tvComment.text=model!!.comment
            binding.tvNumber.text=model.phoneNumber
            binding.tvChangeData.text=model.changeDate
            binding.tvSum.text=model.sum.toString()
        }
    }

    var models:List<Customer?> = listOf()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView=parent.inflate(R.layout.note_history)
        val binding=NoteHistoryBinding.bind(itemView)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount()=models.size
}
