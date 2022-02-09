package com.example.enotebook.extentions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.startActivity
import androidx.viewbinding.ViewBinding
import com.example.enotebook.MainActivity
import com.example.enotebook.helper.SmsHelper.context

fun View.visibility(visibility: Boolean): View {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
    return this
}
fun View.onClick(onClick: (View) -> Unit) {
    this.setOnClickListener(onClick)
}

fun ViewGroup.inflate(@LayoutRes id: Int): View = LayoutInflater.from(context).inflate(id,this,false)

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}


fun toastSH(msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun toastLN(msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}