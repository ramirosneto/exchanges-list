package br.com.ramirosneto.exchanges.app.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import br.com.ramirosneto.exchanges.app.domain.usecase.GetExchangesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getExchangesUseCase: GetExchangesUseCase
    private lateinit var viewModel: ExchangeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getExchangesUseCase = mockk()
        viewModel = ExchangeViewModel(getExchangesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getExchanges - success`() = runTest {
        // Arrange
        val mockExchanges = listOf(
            Exchange("USD", "United States Dollar", "", 2.5),
            Exchange("EUR", "Euro", "", 1.5)
        )
        coEvery { getExchangesUseCase() } returns any()

        // Act
        viewModel.exchanges.test {
            // Assert
            val firstItem = awaitItem()
            assertEquals(emptyList<Exchange>(), firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals(mockExchanges, secondItem)
        }
    }

    @Test
    fun `getExchanges - error`() = runTest {
        // Arrange
        coEvery { getExchangesUseCase() } throws Exception()

        // Act
        viewModel.error.test {
            // Assert
            val firstItem = awaitItem()
            assertEquals(null, firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals("Error loading currencies.", secondItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isLoading - success`() = runTest {
        // Arrange

        coEvery { getExchangesUseCase() } returns any()

        // Act
        viewModel.isLoading.test {
            // Assert
            val firstItem = awaitItem()
            assertEquals(true, firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals(false, secondItem)
        }
    }

    @Test
    fun `isLoading - error`() = runTest {
        // Arrange
        coEvery { getExchangesUseCase() } throws Exception()

        // Act
        viewModel.isLoading.test {
            // Assert
            val firstItem = awaitItem()
            assertEquals(true, firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals(false, secondItem)
        }
    }
}