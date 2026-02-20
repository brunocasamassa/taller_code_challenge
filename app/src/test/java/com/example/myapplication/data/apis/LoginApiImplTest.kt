
package com.example.myapplication.data.apis

import com.example.myapplication.data.database.LoginDao
import com.example.myapplication.data.entities.ResponseStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginApiImplTest {

    private val loginDao: LoginDao = mockk(relaxed = true)
    private val loginApi = LoginApiImpl(loginDao)

    @Test
    fun `getLogin should insert into dao when login is persisted and status is ok`() = runBlocking {
        val userInput = "user"
        val mailInput = "user@hotmail.com"
        val isLoginPersisted = true

        val result = loginApi.getLogin(userInput, mailInput, isLoginPersisted).first()

        if (result.status == ResponseStatus.STATUS_OK) {
            coVerify { loginDao.insertLogin(any()) }
        } else {
            coVerify(exactly = 0) { loginDao.insertLogin(any()) }
        }
    }

    @Test
    fun `getLogin should not insert into dao when login is not persisted`() = runBlocking {
        val userInput = "bruno"
        val mailInput = "brunocasamasahotmail.com"
        val isLoginPersisted = false

        loginApi.getLogin(userInput, mailInput, isLoginPersisted).first()

        coVerify(exactly = 0) { loginDao.insertLogin(any()) }
    }
}
