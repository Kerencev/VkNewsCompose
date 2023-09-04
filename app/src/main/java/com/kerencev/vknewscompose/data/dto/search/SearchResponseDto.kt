package com.kerencev.vknewscompose.data.dto.search

import com.kerencev.vknewscompose.data.dto.suggested.SuggestedDto
import com.kerencev.vknewscompose.data.dto.suggested.SuggestedType

data class SearchResponseDto(
    val response: SearchDto?
)

data class SearchDto(
    val count: Int?,
    val items: List<SearchType>?
)

data class SearchType(
    val type: SuggestedType?,
    val group: SuggestedDto?,
    val profile: SuggestedDto?,
)