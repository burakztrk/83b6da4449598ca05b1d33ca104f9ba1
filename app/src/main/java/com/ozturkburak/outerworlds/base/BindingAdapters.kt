package com.ozturkburak.outerworlds.base

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun TextView.setIntText(value:Int){
    this.text = value.toString()
}