package com.example.enotebook.screens.main.customer.count

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.PersonItemBinding
import com.example.enotebook.screens.extentions.inflate
import com.example.enotebook.screens.extentions.onClick
import java.text.SimpleDateFormat

class AdapterPersonFragment:RecyclerView.Adapter<AdapterPersonFragment.ViewHolderPersonFragment>() {

    var models:MutableList<Customer?> = mutableListOf()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    inner class ViewHolderPersonFragment(private val binding: PersonItemBinding):RecyclerView.ViewHolder(binding.root){
        fun populateModel(model:Customer?,position: Int){
            binding.tvSum.text= model!!.sum.toString()
            binding.tvComment.text=model.comment
            binding.tvDeadline.text="Qaytaratug'in waqti : ${getDateFromUTCTimestamp(model.getDate)}"
            binding.tvData.text="Alg'an waqti: ${model.setDate}"
            binding.btnPay.onClick {
                onClickButton.invoke(model,position)
            }
        }
    }

    private var onClickButton:(customer:Customer,position:Int)->Unit={ customer: Customer, position: Int -> }
    fun onClickListenerButton(onClickButton:(customer:Customer,position:Int)->Unit){
        this.onClickButton=onClickButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPersonFragment {
        val itemView=parent.inflate(R.layout.person_item)
        val binding=PersonItemBinding.bind(itemView)
        return ViewHolderPersonFragment(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderPersonFragment, position: Int) {
        holder.populateModel(models[position],position)
    }

    override fun getItemCount()=models.size

    fun delete(position: Int){
        models.removeAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size)
    }

    private fun getDateFromUTCTimestamp(mTimestamp: Long): String {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.format(mTimestamp*1000).toString()
    }
}
