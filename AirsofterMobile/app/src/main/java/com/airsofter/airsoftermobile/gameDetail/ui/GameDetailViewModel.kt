package com.airsofter.airsoftermobile.gameDetail.ui

import UserManager
import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameDetail.domain.CancelSignUpUseCase
import com.airsofter.airsoftermobile.gameDetail.domain.GameDetailUseCase
import com.airsofter.airsoftermobile.gameDetail.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val gameDetailUseCase: GameDetailUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val cancelSignUpUseCase: CancelSignUpUseCase) : ViewModel() {

    private val _game = MutableLiveData<GameDetailDto>()
    val game: LiveData<GameDetailDto> get() = _game

    private val  _messageToSnackbar = MutableLiveData<Int>()

    fun loadGameDetail(gameId: String) {
        viewModelScope.launch {
            try {
                val gameDetail = gameDetailUseCase.invoke(gameId) ?: throw NullPointerException("Game detail is null")
                _game.value = gameDetail

            } catch (e: Exception) {
                _messageToSnackbar.value = R.string.unknownError
            }
        }
    }

    fun signUpForGame(
        context: Context,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                val result = signUpUseCase.invoke(UserManager.getCurrentUser()?.id, game.value?.id)

                when {
                    result.message.contains("Signed up successfully", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.succesfulJoinGame
                    }
                    result.message.contains("User is already signed up for another game on the same date", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.alreadyInAnotherGameDate
                    }
                    result.message.contains("User is already signed up", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.alreadyInGame
                    }
                    result.message.contains("Game not found", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.gameNotFound
                    }
                    else -> {
                        _messageToSnackbar.value = R.string.unknownError
                    }
                }

                navController.navigate("GameListScreenKey")


                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(_messageToSnackbar.value ?: R.string.unknownError))
                }
            } catch (e: Exception) {
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.unknownError))
                }
            }
        }
    }

    fun onCancelSignUpForGame(
        context: Context,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                val result = cancelSignUpUseCase.invoke(UserManager.getCurrentUser()?.id, game.value?.id)

                if(result){
                    _messageToSnackbar.value = R.string.succesfulCancelSignUp
                } else {
                    _messageToSnackbar.value = R.string.unknownError
                }

                navController.navigate("GameListScreenKey")

                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(_messageToSnackbar.value ?: R.string.unknownError))
                }
            } catch (e: Exception) {
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.unknownError))
                }
            }
        }
    }
}