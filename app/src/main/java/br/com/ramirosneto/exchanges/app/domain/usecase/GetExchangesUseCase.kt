package br.com.ramirosneto.exchanges.app.domain.usecase

import br.com.ramirosneto.exchanges.app.domain.repository.ExchangeRepository

class GetExchangesUseCase(private val repository: ExchangeRepository) {

    suspend operator fun invoke() = repository.getExchanges()
}
