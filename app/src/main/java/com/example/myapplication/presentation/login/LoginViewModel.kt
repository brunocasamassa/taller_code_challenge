package com.example.myapplication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entities.ResponseStatus
import com.example.myapplication.data.repositories.LoginRepository
import com.example.myapplication.domain.LoginDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _userInput = MutableStateFlow("")
    val userInput: StateFlow<String> = _userInput.asStateFlow()

    private val _mailInput = MutableStateFlow("")
    val mailInput: StateFlow<String> = _mailInput.asStateFlow()

    private val _isLoginPersisted = MutableStateFlow(false)
    val isLoginPersisted: StateFlow<Boolean> = _isLoginPersisted.asStateFlow()

    private val _uiState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onUserInputChange(newValue: String) {
        _userInput.value = newValue
    }

    fun onMailInputChange(newValue: String) {
        _mailInput.value = newValue
    }

    fun onLoginPersistedChange(newValue: Boolean) {
        _isLoginPersisted.value = newValue
    }

    fun performLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = LoginState.Loading
            
            delay(2000)

            loginRepository.getLogin(_userInput.value, _mailInput.value, _isLoginPersisted.value).collect { dto ->
                if (dto.status == ResponseStatus.STATUS_OK) {
                    _uiState.value = LoginState.Success(dto)
                } else {
                    _uiState.value = LoginState.Error("Login failed with status: ${dto.status}")
                }
            }
        }
    }
}

/** ui state for login */
sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Error(val message: String?) : LoginState()
    data class Success(val response: LoginDTO) : LoginState()
}
