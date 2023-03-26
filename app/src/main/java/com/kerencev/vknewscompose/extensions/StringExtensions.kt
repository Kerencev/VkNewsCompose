package com.kerencev.vknewscompose.extensions

import android.net.Uri

fun String.encode(): String {
    return Uri.encode(this)
}