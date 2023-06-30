package com.kerencev.vknewscompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.use_cases.change_like_status.ChangeLikeStatusUseCase
import com.kerencev.vknewscompose.domain.use_cases.delete_news.DeleteNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_news.GetNewsUseCase
import com.kerencev.vknewscompose.extensions.removeElement
import com.kerencev.vknewscompose.extensions.updateElement
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
    private val newsModelMapper: NewsModelMapper
) : ViewModel() {

    private val _screenState = MutableStateFlow(HomeState())
    val screenState = _screenState.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        getNewsUseCase()
            .onEach { result ->
                when (result) {
                    is DataResult.Success -> _screenState.emit(
                        _screenState.value.copy(
                            items = _screenState.value.items + result.data.map {
                                newsModelMapper.mapToUi(it)
                            },
                            isLoading = false,
                            isError = false
                        )
                    )

                    is DataResult.Loading -> _screenState.emit(
                        _screenState.value.copy(
                            isLoading = true,
                            isError = false
                        )
                    )

                    is DataResult.Error -> _screenState.emit(
                        _screenState.value.copy(
                            isLoading = false,
                            isError = true
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun changeLikesStatus(newsModelUi: NewsModelUi) {
        changeLikeStatusUseCase(newsModelMapper.mapToEntity(newsModelUi))
            .onEach { result ->
                when (result) {
                    is DataResult.Success -> {
                        _screenState.emit(
                            _screenState.value.copy(
                                items = _screenState.value.items.updateElement(
                                    newsModelUi,
                                    newsModelMapper.mapToUi(result.data)
                                )
                            )
                        )
                    }

                    is DataResult.Loading -> Unit
                    is DataResult.Error -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    fun onNewsItemDismiss(newsModelUi: NewsModelUi) {
        deleteNewsUseCase(newsModelMapper.mapToEntity(newsModelUi))
            .onEach { result ->
                when (result) {
                    is DataResult.Success -> {
                        _screenState.emit(
                            _screenState.value.copy(
                                items = _screenState.value.items.removeElement(newsModelUi)
                            )
                        )
                    }

                    is DataResult.Loading -> Unit
                    is DataResult.Error -> Unit
                }
            }
            .launchIn(viewModelScope)
    }
}

data class HomeState(
    val items: List<NewsModelUi> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)