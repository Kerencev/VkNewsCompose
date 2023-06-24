package com.kerencev.vknewscompose.presentation.extensions

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle

fun <T> Bundle.getParcelableNew(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, clazz)
    } else {
        this.getParcelable(key)
    }
}