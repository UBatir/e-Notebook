package com.example.enotebook.screens.main.customer

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.data.model.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.NoteListNameBinding
import com.example.enotebook.extentions.inflate
import com.example.enotebook.extentions.onClick

class ListNameAdapter : RecyclerView.Adapter<ListNameAdapter.ListNameViewHolder>() {

    inner class ListNameViewHolder(private val binding: NoteListNameBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun populateModel(model: Customer?, position: Int) {
            binding.tvName.text = model!!.name
            binding.tvSum.text = model.sum.toString()
            binding.tvComment.text = model.comment
            binding.root.onClick {
                onItemClick.invoke(model)
            }
            binding.btnOptions.onClick {
                onItemClickOptions.invoke(binding.btnOptions, model,position)
            }
        }
    }

    private var onItemClick:(customer: Customer)->Unit={}
    fun setOnClickItemListener(onItemClick:(customer: Customer)->Unit){
        this.onItemClick=onItemClick
    }

    private var onItemClickOptions: (view: View, customer: Customer, position:Int) -> Unit = { _: View, _: Customer, _: Int -> }
    fun setOnClickItemOptionsListener(onItemClickOptions: (view: View, customer: Customer, position:Int) -> Unit) {
        this.onItemClickOptions = onItemClickOptions
    }

    var models: MutableList<Customer?> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNameViewHolder {
        val itemView = parent.inflate(R.layout.note_list_name)
        val binding = NoteListNameBinding.bind(itemView)
        return ListNameViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListNameViewHolder, position: Int) {
        holder.populateModel(models[position],position)
    }

    override fun getItemCount() = models.size

    fun filterList(filteredListName: MutableList<Customer?>) {
        models = filteredListName;
        notifyDataSetChanged();
    }

    fun delete(position: Int){
        models.removeAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size)
    }

}

