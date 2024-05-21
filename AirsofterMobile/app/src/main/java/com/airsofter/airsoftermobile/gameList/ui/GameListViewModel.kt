package com.airsofter.airsoftermobile.gameList.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airsofter.airsoftermobile.core.model.Game
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import com.airsofter.airsoftermobile.gameList.domain.GameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(private val gameListUseCase: GameListUseCase) : ViewModel() {

    private val _games = MutableLiveData<GameListResponse>()
    val games: LiveData<GameListResponse> get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _locationFilter = MutableLiveData<String>()
    val locationFilter: LiveData<String> get() = _locationFilter

    private var allGames: List<Game> = emptyList()

    init {
        fetchGames()
    }

    fun refresh() {
        fetchGames()
    }

    fun updateLocationFilter(province: String) {
        _locationFilter.value = province
    }

    fun applyFilter() {
        val filter = _locationFilter.value.orEmpty().trim()

        val filteredGames = if (filter.isNotBlank()) {
            allGames.filter { it.location.contains(filter, ignoreCase = true) }
        } else {
            allGames
        }

        _games.value = GameListResponse(filteredGames)
    }

    private fun fetchGames() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val gameList = gameListUseCase.invoke() ?: throw NullPointerException("Games list is null")
                allGames = gameList.games // Almacena todos los juegos recuperados
                _games.postValue(gameList) // Actualiza la lista de juegos observables
                Log.i("INFOGAME", gameList.toString())
            } catch (e: Exception) {
                _error.value = "Error fetching games: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
