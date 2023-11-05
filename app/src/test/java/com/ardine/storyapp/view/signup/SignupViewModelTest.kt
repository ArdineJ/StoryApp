package com.ardine.storyapp.view.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ardine.storyapp.DataDummy
import com.ardine.storyapp.data.Repository
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.data.response.RegisterResponse
import com.ardine.storyapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SignupViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: Repository
    private lateinit var viewModel: SignupViewModel
    private val dummyRegister = DataDummy.generateDummyRegisterResponse()

    private val name = "name"
    private val email = "email@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = SignupViewModel(storyRepository)
    }

    @Test
    fun `when user Register is Success`() {
        val expectedUser = MutableLiveData<ResultState<RegisterResponse>>()
        expectedUser.value = ResultState.Success(dummyRegister)

        Mockito.`when`(storyRepository.register(name, email, password)).thenReturn(expectedUser)

        val actualUser = viewModel.register(name, email, password).getOrAwaitValue()

        Mockito.verify(storyRepository).register(name, email, password)

        assertNotNull(actualUser)
        assertTrue(actualUser is ResultState.Success)
    }
}
