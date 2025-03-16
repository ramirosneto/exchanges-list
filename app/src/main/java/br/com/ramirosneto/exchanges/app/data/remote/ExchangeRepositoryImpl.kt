package br.com.ramirosneto.exchanges.app.data.remote

import br.com.ramirosneto.exchanges.app.data.remote.api.ApiService
import br.com.ramirosneto.exchanges.app.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.flow

class ExchangeRepositoryImpl(private val apiService: ApiService) : ExchangeRepository {

    override suspend fun getExchanges() = flow {
        emit(apiService.getExchanges())
    }
}