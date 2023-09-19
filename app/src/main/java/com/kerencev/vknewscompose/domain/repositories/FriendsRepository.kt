package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.PagingModel
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    /**
     * Get list of users friends
     * @param userId - Id of the user for whom the friends list will be uploaded
     * @param searchText - The text by which the list of friends will be received
     * @param isRefresh - The list of friends is updated or added
     */
    fun getFriends(
        userId: Long,
        searchText: String,
        isRefresh: Boolean
    ): Flow<PagingModel<UserProfileModel>>
}