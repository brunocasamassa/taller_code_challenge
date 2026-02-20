package com.example.myapplication.data.repositories

import com.example.myapplication.domain.LoginDTO
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun getLogin(user: String, mail: String, isLoginPersisted: Boolean): Flow<LoginDTO>
}
