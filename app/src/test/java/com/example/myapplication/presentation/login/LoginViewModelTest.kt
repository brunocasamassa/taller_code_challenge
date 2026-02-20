package com.example.myapplication.presentation.login

import app.cash.turbine.test
import com.example.myapplication.data.entities.ResponseStatus
import com.example.myapplication.data.repositories.LoginRepository
import com.example.myapplication.domain.LoginDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val loginRepository: LoginRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `performLogin should emit Loading and then Success on successful login`() = runTest {
        val userInput = "bruno"
        val mailInput = "brunocasamassa@hotmail.com"
        val isLoginPersisted = true
        val loginDTO = LoginDTO(userInput, mailInput, ResponseStatus.STATUS_OK)

        coEvery { loginRepository.getLogin(userInput, mailInput, isLoginPersisted) } returns flowOf(
            loginDTO
        )

        with(viewModel) {
            onUserInputChange(userInput)
            onMailInputChange(mailInput)
            onLoginPersistedChange(isLoginPersisted)
            performLogin()
            uiState.test {
                assertEquals(LoginState.Loading, awaitItem())
                assertEquals(LoginState.Success(loginDTO), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    }

    @Test
    fun `performLogin should emit Loading and then Error on failed login`() = runTest {
        val userInput = "testUser"
        val mailInput = "test@example.com"
        val isLoginPersisted = false
        val loginDTO = LoginDTO(userInput, mailInput, ResponseStatus.STATUS_ERROR)

        coEvery { loginRepository.getLogin(userInput, mailInput, isLoginPersisted) } returns flowOf(
            loginDTO
        )

        with(viewModel) {
            onUserInputChange(userInput)
            onMailInputChange(mailInput)
            onLoginPersistedChange(isLoginPersisted)
            performLogin()
            uiState.test {
                assertEquals(LoginState.Loading, awaitItem())
                assertEquals(
                    LoginState.Error("Login failed with status: ${loginDTO.status}"),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }

        }
    }
}
