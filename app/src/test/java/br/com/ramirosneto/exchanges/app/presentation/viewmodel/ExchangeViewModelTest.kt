package br.com.ramirosneto.exchanges.app.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import br.com.ramirosneto.exchanges.app.domain.repository.ExchangeRepository
import br.com.ramirosneto.exchanges.app.domain.usecase.GetExchangesUseCase
import br.com.ramirosneto.exchanges.app.presentation.model.ExchangeDTO
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
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: ExchangeRepository
    private lateinit var getExchangesUseCase: GetExchangesUseCase
    private lateinit var viewModel: ExchangeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        getExchangesUseCase = mockk(relaxed = true)
        viewModel = ExchangeViewModel(getExchangesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getExchanges - success`() = runTest {
        val mockExchanges = listOf(
            ExchangeDTO("USD", "United States Dollar", "", "2.5"),
            ExchangeDTO("EUR", "Euro", "", "1.5")
        )
        coEvery { getExchangesUseCase() } returns flowOf(mockExchanges)

        viewModel.exchanges.test {
            val firstItem = awaitItem()
            assertEquals(emptyList<ExchangeDTO>(), firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals(mockExchanges, secondItem)
        }
    }

    @Test
    fun `getExchanges - error`() = runTest {
        coEvery { getExchangesUseCase() } throws Exception()

        viewModel.error.test {
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
        val mockExchanges = listOf(
            ExchangeDTO("USD", "United States Dollar", "", "2.5"),
            ExchangeDTO("EUR", "Euro", "", "1.5")
        )
        coEvery { getExchangesUseCase() } returns flowOf(mockExchanges)

        viewModel.isLoading.test {
            val firstItem = awaitItem()
            assertEquals(true, firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals(false, secondItem)
        }
    }

    @Test
    fun `isLoading - error`() = runTest {
        coEvery { getExchangesUseCase() } throws Exception()

        viewModel.isLoading.test {
            val firstItem = awaitItem()
            assertEquals(true, firstItem)
            testDispatcher.scheduler.advanceUntilIdle()
            val secondItem = awaitItem()
            assertEquals(false, secondItem)
        }
    }
}
