package com.kerencev.vknewscompose.presentation.utils.extensions

fun Int.formatStatisticCount(): String {
    return if (this > 100_000) {
        String.format("%sK", (this / 1_000))
    } else if (this > 1_000) {
        String.format("%.1fK", (this / 1_000f))
    } else {
        this.toString()
    }
}