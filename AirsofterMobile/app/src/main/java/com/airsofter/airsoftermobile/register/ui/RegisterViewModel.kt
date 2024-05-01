package com.airsofter.airsoftermobile.register.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airsofter.airsoftermobile.register.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) : ViewModel() {
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

    fun onRegisterChange(username: String, displayName: String, email: String, password: String, confirmPassword: String) {
        _username.value = username
        _displayName.value = displayName
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
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
}