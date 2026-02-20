package com.example.myapplication.data.apis

import com.example.myapplication.data.database.LoginDao
import com.example.myapplication.data.entities.LoginEntity
import com.example.myapplication.data.entities.ResponseStatus
import com.example.myapplication.domain.LoginDTO
import com.example.myapplication.utils.toDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.random.Random

class LoginApiImpl @Inject constructor(
    private val loginDao: LoginDao
) : LoginApi {
    override fun getLogin(
        userInput: String,
        mailInput: String,
        isLoginPersisted: Boolean
    ): Flow<LoginDTO> {
        val randomStatus = if (Random.nextBoolean()) {
            ResponseStatus.STATUS_OK
        } else {
            ResponseStatus.STATUS_ERROR
        }

        val loginEntity = LoginEntity(
            id = Random.nextInt(),
            userName = userInput,
            email = mailInput,
            status = randomStatus
        )


        /*persisting dao into database*/
        return flowOf(loginEntity.toDTO()).onEach {
            if (isLoginPersisted && loginEntity.status == ResponseStatus.STATUS_OK) {
                loginDao.insertLogin(loginEntity)

            }
        }
    }
}
