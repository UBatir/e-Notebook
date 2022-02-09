package com.example.enotebook.screens.main.customer

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.App
import com.example.enotebook.data.model.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.NoteListNameBinding
import com.example.enotebook.extentions.inflate
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.scope

class ListNameAdapter : RecyclerView.Adapter<ListNameAdapter.ListNameViewHolder>() {

    var query: String = ""

    inner class ListNameViewHolder(private val binding: NoteListNameBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun populateModel(model: Customer, position: Int) = binding.scope {
            paintText(model.name)
            tvSum.text = model.sum.toString()
            tvComment.text = model.comment
            root.onClick {
                onItemClick.invoke(model)
            }
            btnOptions.onClick {
                onItemClickOptions.invoke(btnOptions, model,position)
            }
        }

        private fun paintText(name:String) = binding.scope {
            val spanSt = SpannableString(name)
            val foregroundColorSpan =
                ForegroundColorSpan(ContextCompat.getColor(App.instance, R.color.red))
            val startIndex = name.indexOf(query, 0, true)
            val endIndex = startIndex + query.length
            if (startIndex > -1) {
                spanSt.setSpan(
                    foregroundColorSpan,
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            tvName.text = spanSt
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

    var models: MutableList<Customer> = mutableListOf()
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

    fun delete(position: Int){
        models.removeAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size)
    }

}

