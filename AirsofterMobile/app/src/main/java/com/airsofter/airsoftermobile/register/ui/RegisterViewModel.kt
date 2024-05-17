package com.airsofter.airsoftermobile.register.ui

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.login.data.network.model.UserDTO
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

    suspend fun isUsernameAvailable(username: String): Boolean {
        return !registerRepository.checkFieldAvailability("username", username)
    }

    suspend fun isDisplayNameAvailable(displayName: String): Boolean {
        return !registerRepository.checkFieldAvailability("displayName", displayName)
    }

    suspend fun isEmailAvailable(email: String): Boolean {
        return !registerRepository.checkFieldAvailability("email", email)
    }


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

    fun onRegisterPressed(context: Context, scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (!isUsernameAvailable(username.value!!)) {
                    scope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.usernameExists))
                    }
                    return@launch
                }
                if (!isDisplayNameAvailable(displayName.value!!)) {
                    scope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.displayNameExists))
                    }
                    return@launch
                }
                if (!isEmailAvailable(email.value!!)) {
                    scope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.emailExists))
                    }
                    return@launch
                }

                // Si todos los campos están disponibles, realiza el registro
                val result = registerUseCase.invoke(username.value!!, displayName.value!!, email.value!!, password.value!!)
                Log.i("INFO RESULT", result.toString())
            } catch (e: Exception) {
                Log.e("REGISTER", "Error during register: ${e.message}", e)
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.unknownError))
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

}