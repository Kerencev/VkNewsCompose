package com.kerencev.vknewscompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.domain.use_cases.change_like_status.ChangeLikeStatusUseCase
import com.kerencev.vknewscompose.domain.use_cases.delete_news.DeleteNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_news.GetNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.load_next_news.LoadNextNewsUseCase
import com.kerencev.vknewscompose.extensions.mergeWith
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val loadNexNewsUseCase: LoadNextNewsUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
    private val newsModelMapper: NewsModelMapper
) : ViewModel() {

    private val newsFlow = getNewsUseCase()

    private val loadNexDataFlow = MutableSharedFlow<ScreenState<Home>>()

    val screenState = newsFlow
        .filter { it.isNotEmpty() }
        .map { newsModels ->
            val newsModelUi = newsModels.map { newsModelMapper.mapToUi(it) }
            ScreenState.Content(Home(newsModelUi)) as ScreenState<Home>
        }
        .onStart { emit(ScreenState.Loading) }
        .mergeWith(loadNexDataFlow)

    fun loadNextNews() {
        viewModelScope.launch {
            loadNexDataFlow.emit(
                ScreenState.Content(
                    data = Home(
                        news = newsFlow.value.map { newsModelMapper.mapToUi(it) },
                        nextDataIsLoading = true
                    )
                )
            )
            loadNexNewsUseCase()
        }
    }

    fun changeLikesStatus(newsModelUi: NewsModelUi) {
        viewModelScope.launch {
            changeLikeStatusUseCase(newsModelMapper.mapToEntity(newsModelUi))
        }
    }

    fun onNewsItemDismiss(newsModelUi: NewsModelUi) {
        viewModelScope.launch {
            deleteNewsUseCase(newsModelMapper.mapToEntity(newsModelUi))
        }
    }

}

data class Home(
    val news: List<NewsModelUi>,
    val nextDataIsLoading: Boolean = false
)