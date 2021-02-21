package com.example.enotebook.screens.main.customer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.NoteListNameBinding
import com.example.enotebook.screens.extentions.inflate

class ListNameAdapter:RecyclerView.Adapter<ListNameAdapter.ListNameViewHolder>() {

    inner class ListNameViewHolder(private val binding: NoteListNameBinding):RecyclerView.ViewHolder(
        binding.root
    ){
        fun populateModel(model: Customer?){
            binding.tvName.text=model!!.name
            binding.tvSumma.text=model.sum.toString()
            binding.tvComment.text=model.comment
        }
    }

    var models:List<Customer?> = listOf()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNameViewHolder {
        val itemView=parent.inflate(R.layout.note_list_name)
        val binding=NoteListNameBinding.bind(itemView)
        return ListNameViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListNameViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() =models.size

    fun filterList(filteredListName: ArrayList<Customer>) {
        models = filteredListName;
        notifyDataSetChanged();
    }
}

