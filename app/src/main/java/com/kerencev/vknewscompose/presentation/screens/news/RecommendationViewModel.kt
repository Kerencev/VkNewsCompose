package com.kerencev.vknewscompose.presentation.screens.news

import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.GetNewsFeature
import javax.inject.Inject

class RecommendationViewModel @Inject constructor(
    getNewsFeature: GetNewsFeature,
    changeLikeStatusFeature: ChangeLikeStatusFeature,
) : NewsViewModel(
    params = NewsParams(type = NewsType.RECOMMENDATION),
    getNewsFeature,
    changeLikeStatusFeature
)