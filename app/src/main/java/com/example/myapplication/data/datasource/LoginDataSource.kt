package com.example.myapplication.data.datasource

import com.example.myapplication.domain.LoginDTO
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {
    fun getLogin(userInput:String, mailInput:String, isLoginPersisted: Boolean): Flow<LoginDTO>
}
