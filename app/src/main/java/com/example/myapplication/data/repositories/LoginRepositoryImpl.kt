package com.example.myapplication.data.repositories

import com.example.myapplication.data.datasource.LoginDataSource
import com.example.myapplication.domain.LoginDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override fun getLogin(user: String, mail: String, isLoginPersisted: Boolean): Flow<LoginDTO> {
        return loginDataSource.getLogin(user, mail, isLoginPersisted)
    }
}
