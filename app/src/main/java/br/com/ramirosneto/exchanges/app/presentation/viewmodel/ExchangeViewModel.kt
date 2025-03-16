package br.com.ramirosneto.exchanges.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import br.com.ramirosneto.exchanges.app.domain.usecase.GetExchangesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExchangeViewModel(private val getExchangesUseCase: GetExchangesUseCase) : ViewModel() {

    private val _exchanges = MutableStateFlow<List<Exchange>>(emptyList())
    val exchanges: StateFlow<List<Exchange>> get() = _exchanges

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchExchanges()
    }

    private fun fetchExchanges() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                getExchangesUseCase.invoke().collect {
                    _exchanges.value = it
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Houve um erro ao carregar a lista de exchanges"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshExchanges() {
        fetchExchanges()
    }
}
