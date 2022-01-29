package com.example.enotebook.extentions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

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