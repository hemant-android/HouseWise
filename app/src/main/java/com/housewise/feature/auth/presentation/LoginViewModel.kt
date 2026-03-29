package com.housewise.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewise.HousewiseApp
import com.housewise.core.utils.Resource
import com.housewise.feature.auth.data.model.LoginRequest
import com.housewise.feature.auth.data.model.LoginResponse
import com.housewise.feature.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository()

    // Holds the current state of the login process
    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle())
    val loginState: StateFlow<Resource<LoginResponse>> = _loginState

    fun login(email: String, pass: String) {
        // Remove any hidden trailing spaces the Android keyboard added!
        val cleanEmail = email.trim()
        val cleanPass = pass.trim()
        // Basic validation
        if (cleanEmail.isBlank() || cleanPass.isBlank()) {
            _loginState.value = Resource.Error("Email and Password cannot be empty")
            return
        }

        viewModelScope.launch {
            repository.login(LoginRequest(cleanEmail, cleanPass)).onEach { result ->

                // If login is successful, save the token and userId!
                if (result is Resource.Success) {
                    val responseData = result.data
                    // Save Token
                    responseData?.token?.let { token ->
                        HousewiseApp.sessionManager.saveAuthToken(token)
                    }
                    // Save User ID
                    responseData?.userId?.let { id ->
                        HousewiseApp.sessionManager.saveUserId(id)
                    }
                    // Save First Name
                    responseData?.firstName?.let { firstName ->
                        HousewiseApp.sessionManager.saveFirstName(firstName)
                    }
                    // Save Last Name
                    responseData?.lastName?.let { lastName ->
                        HousewiseApp.sessionManager.saveLastName(lastName)
                    }
                }

                _loginState.value = result
            }.launchIn(this)
        }
    }

    // Optional: Call this to reset state after handling an error toast
    fun resetState() {
        _loginState.value = Resource.Idle()
    }
}