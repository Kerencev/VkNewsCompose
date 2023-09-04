package com.kerencev.vknewscompose.domain.entities

/**
 * A model for data using pagination
 */
data class PagingModel<T>(
    val data: List<T>,
    val isItemsOver: Boolean
)