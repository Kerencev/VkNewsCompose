package com.kerencev.vknewscompose.presentation.screens.search.flow

import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction

sealed class SearchInputAction : VkAction {

    /**
     * Get list of users and communities
     * @param query - The text by which the list of users and communities will be received
     * @param isRefresh - The list of users and communities is updated or added
     */
    class GetData(val query: String, val isRefresh: Boolean) :
        SearchInputAction()
}

sealed class SearchOutputAction : VkAction {

    /**
     * Set data on success
     */
    class SetData(
        val query: String,
        val data: List<Profile>,
        val isItemsOver: Boolean
    ) : SearchOutputAction()

    /**
     * Set loading state
     * @param isRefresh - The list of users and communities is updated or added
     */
    class Loading(val isRefresh: Boolean, val query: String) : SearchOutputAction()

    /**
     * Set error state
     */
    class Error(val message: String, val query: String) : SearchOutputAction()

}