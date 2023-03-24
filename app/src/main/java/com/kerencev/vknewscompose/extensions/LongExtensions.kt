package com.kerencev.vknewscompose.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.toDateTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd.MM.yy HH:mm")
    return format.format(date)
}
