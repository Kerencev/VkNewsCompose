package com.kerencev.vknewscompose.extensions

fun String?.notNullOrEmptyOrBlank(): Boolean = !isNullOrBlank()