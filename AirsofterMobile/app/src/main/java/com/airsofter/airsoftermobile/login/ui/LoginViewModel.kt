package com.airsofter.airsoftermobile.login.ui

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> = _snackbarMessage

    fun onLoginChange(username: String, password: String) {
        _username.value = username
        _password.value = password
        _loginEnable.value = username.isNotBlank() && password.isNotBlank()
    }

    fun onLoginPressed(context: Context, scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase.invoke(username.value!!, password.value!!)
            if (result) {
                Log.i("LOGIN", "Login successful")
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.login_success))
                }
                // Aquí puedes realizar alguna acción después de que el inicio de sesión sea exitoso, como navegar a otra pantalla
            } else {
                Log.i("LOGIN", "Login failed")
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.login_error))
                }
                // Aquí puedes manejar el caso en que el inicio de sesión falle, por ejemplo, mostrar un mensaje de error al usuario
            }
            _isLoading.value = false
        }
    }
}