package com.airsofter.airsoftermobile.gameDetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airsofter.airsoftermobile.core.model.Game
import com.airsofter.airsoftermobile.gameDetail.data.network.model.GameDetailDto
import com.airsofter.airsoftermobile.gameDetail.data.network.response.GameDetailResponse
import com.airsofter.airsoftermobile.gameDetail.domain.GameDetailUseCase
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import com.airsofter.airsoftermobile.gameList.domain.GameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val gameDetailUseCase: GameDetailUseCase) : ViewModel() {

    private val _game = MutableLiveData<GameDetailDto>()
    val game: LiveData<GameDetailDto> get() = _game

    fun loadGameDetail(gameId: String) {
        viewModelScope.launch {
            try {
                val gameDetail = gameDetailUseCase.invoke(gameId)?.gameDetailToLoad ?: throw NullPointerException("Game detail is null")
                _game.value = gameDetail

            } catch (e: Exception) {
                // Handle error
                // You can set a separate LiveData for errors if needed
            }
        }
    }
}