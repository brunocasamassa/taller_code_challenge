package com.example.myapplication.data.apis

import com.example.myapplication.data.entities.LoginEntity
import com.example.myapplication.domain.LoginDTO
import kotlinx.coroutines.flow.Flow

interface LoginApi {
    fun getLogin(userInput: String, mailInput: String, isLoginPersisted: Boolean): Flow<LoginDTO>
}
