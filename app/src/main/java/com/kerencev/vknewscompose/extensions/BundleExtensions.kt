package com.kerencev.vknewscompose.extensions

import android.os.Build
import android.os.Bundle

fun <T> Bundle.getParcelableNew(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        this.getParcelable(key, clazz)
    } else {
        this.getParcelable(key)
    }
}