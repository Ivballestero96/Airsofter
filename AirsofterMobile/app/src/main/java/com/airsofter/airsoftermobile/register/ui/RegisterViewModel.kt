package com.airsofter.airsoftermobile.register.ui

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.register.data.RegisterRepository
import com.airsofter.airsoftermobile.register.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase, private val registerRepository: RegisterRepository) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _displayName = MutableLiveData<String>()
    val displayName: LiveData<String> = _displayName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable: LiveData<Boolean> = _registerEnable

    private val _registrationError = MutableLiveData<String>()
    val registrationError: LiveData<String> = _registrationError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val  _messageToSnackbar = MutableLiveData<Int>()
    val messageToSnackbar:LiveData<Int> = _messageToSnackbar

    fun onRegisterChange(username: String, displayName: String, email: String, password: String, confirmPassword: String) {
        _username.value = username
        _displayName.value = displayName
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _registerEnable.value = validateFields()
    }

    private fun validateFields() : Boolean {
        val isUsernameValid = !_username.value.isNullOrEmpty()
        val isDisplayNameValid = !_displayName.value.isNullOrEmpty()
        val isEmailValid = isValidEmail(_email.value)
        val isPasswordValid = !_password.value.isNullOrEmpty()
        val isConfirmPasswordValid = !_confirmPassword.value.isNullOrEmpty()
        val doPasswordsMatch = _password.value == _confirmPassword.value

        return  isUsernameValid && isDisplayNameValid && isEmailValid &&
                isPasswordValid && isConfirmPasswordValid && doPasswordsMatch
    }

    private fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun onRegisterPressed(
        context: Context,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val result = registerUseCase.invoke(username.value!!, displayName.value!!, email.value!!, password.value!!)


                when {
                    result.message.contains("Username taken", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.usernameExists
                    }
                    result.message.contains("Email taken", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.emailExists
                    }
                    result.message.contains("DisplayName taken", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.displayNameExists
                    }
                    result.message.contains("OK", ignoreCase = true) -> {
                        _messageToSnackbar.value = R.string.register_succesful
                    }
                    else -> {
                        _messageToSnackbar.value = R.string.unknownError
                    }
                }

                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(_messageToSnackbar.value ?: R.string.unknownError))
                }

                if(result.success){
                    resetValues()
                    navController.navigate("LoginScreenKey")
                }

                Log.i("INFO RESULT", result.message)
            } catch (e: Exception) {
                Log.e("REGISTER", "Error during register: ${e.message}", e)
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(_messageToSnackbar.value ?: R.string.unknownError))
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun resetValues(){
        _username.value = ""
        _displayName.value = ""
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
    }
}