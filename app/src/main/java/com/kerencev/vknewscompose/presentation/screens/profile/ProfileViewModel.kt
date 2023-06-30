package com.kerencev.vknewscompose.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.domain.use_cases.get_wall.GetWallUseCase
import com.kerencev.vknewscompose.domain.use_cases.profile.GetProfilePhotosUseCase
import com.kerencev.vknewscompose.domain.use_cases.profile.GetProfileUseCase
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getWallUseCase: GetWallUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfilePhotosUseCase: GetProfilePhotosUseCase,
    private val newsModelMapper: NewsModelMapper
) : ViewModel() {

    private val _profileState = MutableStateFlow<ScreenState<ProfileModel>>(ScreenState.Loading)
    val profileState = _profileState.asStateFlow()

    private val _photosState = MutableStateFlow<ScreenState<List<PhotoModel>>>(ScreenState.Loading)
    val photosState = _photosState.asStateFlow()

    private val _wallState = MutableStateFlow<WallState>(WallState(emptyList(), isLoading = true))
    val wallState = _wallState.asStateFlow()

    private var wallPage = 0
    private var wallPostsTotalCount = 0

    init {
        loadProfile()
        loadProfilePhotos()
        loadWall()
    }

    fun loadProfile() {
        getProfileUseCase()
            .onEach { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> _profileState.emit(ScreenState.Content(dataResult.data))
                    is DataResult.Loading -> _profileState.emit(ScreenState.Loading)
                    is DataResult.Error -> _profileState.emit(ScreenState.Error(dataResult.throwable))
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadProfilePhotos() {
        getProfilePhotosUseCase()
            .onEach { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> _photosState.emit(ScreenState.Content(dataResult.data))
                    is DataResult.Loading -> _photosState.emit(ScreenState.Loading)
                    is DataResult.Error -> _photosState.emit(ScreenState.Error(dataResult.throwable))
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadWall() {
        if (wallPage != 0 && wallPostsTotalCount == _wallState.value.items.size) return
        getWallUseCase(wallPage)
            .onEach { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        wallPage++
                        wallPostsTotalCount = dataResult.data.totalCount
                        val data = dataResult.data.items.map { newsModelMapper.mapToUi(it) }
                        _wallState.emit(WallState(items = _wallState.value.items + data))
                    }

                    is DataResult.Loading -> _wallState.emit(
                        _wallState.value.copy(
                            isLoading = true,
                            isError = false
                        )
                    )

                    is DataResult.Error -> _wallState.emit(
                        _wallState.value.copy(
                            isLoading = false,
                            isError = true
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

data class WallState(
    val items: List<NewsModelUi>,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
