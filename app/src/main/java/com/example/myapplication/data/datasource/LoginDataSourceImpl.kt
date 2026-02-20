package com.example.myapplication.data.datasource

import com.example.myapplication.data.apis.LoginApi
import com.example.myapplication.domain.LoginDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val api: LoginApi
) : LoginDataSource{
    override fun getLogin(userInput:String, mailInput:String, isLoginPersisted: Boolean): Flow<LoginDTO> {
        return api.getLogin(userInput, mailInput, isLoginPersisted)
    }
}
