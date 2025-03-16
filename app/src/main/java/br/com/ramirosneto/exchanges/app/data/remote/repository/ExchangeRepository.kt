package br.com.ramirosneto.exchanges.app.data.remote.repository

import br.com.ramirosneto.exchanges.app.data.remote.api.ApiService
import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExchangeRepository(private val apiService: ApiService) {

    fun getExchanges(): Flow<List<Exchange>> = flow {
        emit(apiService.getExchanges())
    }
}
