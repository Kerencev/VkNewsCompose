package com.kerencev.vknewscompose.extensions

fun Int.formatStatisticCount(): String {
    return if (this > 1_000_000) {
        String.format("%.1fM", (this / 1_000_000f))
    } else if (this > 100_000) {
        String.format("%sK", (this / 1_000))
    } else if (this > 1_000) {
        String.format("%.1fK", (this / 1_000f))
    } else {
        this.toString()
    }
}
