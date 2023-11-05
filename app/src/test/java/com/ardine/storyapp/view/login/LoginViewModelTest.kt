package com.ardine.storyapp.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ardine.storyapp.DataDummy
import com.ardine.storyapp.data.Repository
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.data.response.LoginResponse
import com.ardine.storyapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: Repository
    private lateinit var viewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLoginResponse()

    private val email = "email@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        viewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when user Login is Success`() {
        val expectedUser = MutableLiveData<ResultState<LoginResponse>>()
        expectedUser.value = ResultState.Success(dummyLogin)
        Mockito.`when`(storyRepository.login(email, password)).thenReturn(expectedUser)

        val actualUser = viewModel.login(email, password).getOrAwaitValue()

        Mockito.verify(storyRepository).login(email, password)
        assertNotNull(actualUser)
        assertTrue(actualUser is ResultState.Success)
    }
}
