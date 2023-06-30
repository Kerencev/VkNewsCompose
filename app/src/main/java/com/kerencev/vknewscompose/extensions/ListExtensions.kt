package com.kerencev.vknewscompose.extensions

fun <T> List<T>.updateElement(targetElement: T, updatedElement: T): List<T> {
    val data = this.toMutableList()
    val index = data.indexOf(targetElement)
    if (index == -1) return this
    data[index] = updatedElement
    return data.toList()
}

fun <T> List<T>.removeElement(element: T): List<T> {
    return this.toMutableList().apply { remove(element) }.toList()
}