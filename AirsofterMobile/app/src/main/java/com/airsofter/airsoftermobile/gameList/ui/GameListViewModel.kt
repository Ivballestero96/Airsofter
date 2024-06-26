package com.airsofter.airsoftermobile.gameList.ui

import UserManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameList.domain.GameListUseCase
import com.airsofter.airsoftermobile.gameList.domain.NextGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val gameListUseCase: GameListUseCase,
    private val nextGameUseCase: NextGameUseCase
) : ViewModel() {

    private val _games = MutableLiveData<List<GameDetailDto>>()
    val games: LiveData<List<GameDetailDto>> get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _locationFilter = MutableLiveData<String>()
    val locationFilter: LiveData<String> get() = _locationFilter

    private val _nextGame = MutableLiveData<GameDetailDto?>()
    val nextGame: LiveData<GameDetailDto?> get() = _nextGame

    private var allGames: List<GameDetailDto> = emptyList()

    init {
        fetchGames()
    }

    fun refresh() {
        fetchGames()
        getNextGame(UserManager.getCurrentUser()?.id.toString())
    }

    fun updateLocationFilter(province: String) {
        _locationFilter.value = province
    }

    fun applyFilter() {
        val filter = _locationFilter.value.orEmpty().trim()

        val filteredGames = if (filter.isNotBlank()) {
            allGames.filter { it.provinceName.contains(filter, ignoreCase = true) }
        } else {
            allGames
        }

        _games.value = filteredGames
    }

    private fun getNextGame(id: String?) {
        viewModelScope.launch {
            try {
                val nextGame = nextGameUseCase(id)
                _nextGame.postValue(nextGame)
            } catch (e: Exception) {
                _error.value = "Error fetching next game: ${e.message}"
            }
        }
    }

    private fun fetchGames() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val gameList = gameListUseCase.invoke() ?: throw NullPointerException("Games list is null")
                allGames = gameList
                _games.postValue(gameList)
                getNextGame(UserManager.getCurrentUser()?.id)
            } catch (e: Exception) {
                _error.value = "Error fetching games: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
