package com.kerencev.vknewscompose.presentation.extensions

import android.net.Uri

fun String.encode(): String {
    return Uri.encode(this)
}