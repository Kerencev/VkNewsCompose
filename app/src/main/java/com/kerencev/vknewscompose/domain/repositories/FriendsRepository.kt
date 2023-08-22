package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.FriendsModel
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    /**
     * Get list of users's friends
     * @param searchText - The text by which the list of friends will be received
     * @param isRefresh - The list of friends is updated or added
     */
    fun getFriends(searchText: String, isRefresh: Boolean): Flow<FriendsModel>
}