package com.airsofter.airsoftermobile.register.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airsofter.airsoftermobile.register.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onRegisterChange(
        username: String,
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        _username.value = username
        _displayName.value = displayName
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword

        validateFields()
    }

    fun validateFields() {
        val usernameValue = _username.value
        val displayNameValue = _displayName.value
        val emailValue = _email.value
        val passwordValue = _password.value
        val confirmPasswordValue = _confirmPassword.value

        val isUsernameValid = !usernameValue.isNullOrEmpty()
        val isDisplayNameValid = !displayNameValue.isNullOrEmpty()
        val isEmailValid = !emailValue.isNullOrEmpty()
        val isPasswordValid = !passwordValue.isNullOrEmpty()
        val isConfirmPasswordValid = !confirmPasswordValue.isNullOrEmpty()
        val doPasswordsMatch = passwordValue == confirmPasswordValue

        _registerEnable.value = isUsernameValid && isDisplayNameValid && isEmailValid &&
                isPasswordValid && isConfirmPasswordValid && doPasswordsMatch
    }

    fun onRegisterPressed() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = registerUseCase.invoke(
                username.value!!,
                displayName.value!!,
                email.value!!,
                password.value!!
            )

            Log.i("INFORMATION TESTINT", result.message)
            if (result.success) {
                Log.i("LOGIN", "Login successful")

            } else {
                Log.i("LOGIN", "Login failed")
                // Aquí puedes manejar el caso en que el inicio de sesión falle, por ejemplo, mostrar un mensaje de error al usuario
            }
            _isLoading.value = false
        }
    }
}