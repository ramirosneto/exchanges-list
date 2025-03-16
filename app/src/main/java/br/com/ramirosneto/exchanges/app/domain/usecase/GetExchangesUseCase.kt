package br.com.ramirosneto.exchanges.app.domain.usecase

import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import br.com.ramirosneto.exchanges.app.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

class GetExchangesUseCase(private val repository: ExchangeRepository) {

    suspend operator fun invoke(): Flow<List<Exchange>> {
        return repository.getExchanges()
    }
}
