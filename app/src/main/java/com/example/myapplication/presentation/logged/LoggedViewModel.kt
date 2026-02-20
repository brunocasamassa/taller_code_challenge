package com.example.myapplication.presentation.logged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.LoginDao
import com.example.myapplication.data.entities.LoginEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoggedViewModel @Inject constructor(
    private val loginDao: LoginDao
) : ViewModel() {

    val loggedUser: StateFlow<LoginEntity?> = loginDao.getLoggedUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun onLogoffClicked() {
        viewModelScope.launch {
            loginDao.deleteLogin()
        }
    }
}
