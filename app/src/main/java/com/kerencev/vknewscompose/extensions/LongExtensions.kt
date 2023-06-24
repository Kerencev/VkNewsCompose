package com.kerencev.vknewscompose.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())
    return format.format(date)
}
