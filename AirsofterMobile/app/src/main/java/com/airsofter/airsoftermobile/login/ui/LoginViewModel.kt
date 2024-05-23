package com.airsofter.airsoftermobile.login.ui

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.core.model.UserToLoad
import com.airsofter.airsoftermobile.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase ) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChange(username: String, password: String) {
        _username.value = username
        _password.value = password
        _loginEnable.value = username.isNotBlank() && password.isNotBlank()
    }

    fun onLoginPressed(
        context: Context,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase.invoke(username.value!!, password.value!!)
            if (result != null) {
                Log.i("LOGIN", "Login successful")
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.login_success))
                }

                val userToLoad = result.userToLoad

                val user = UserToLoad(
                    id = userToLoad.id,
                    username = userToLoad.username,
                    displayName = userToLoad.displayName ,
                    email = userToLoad.email
                )

                UserManager.setCurrentUser(user)
                navController.navigate("HomeScreenKey")
            } else {
                Log.i("LOGIN", "Login failed")
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.login_failed))
                }
            }
            _isLoading.value = false
        }
    }
}