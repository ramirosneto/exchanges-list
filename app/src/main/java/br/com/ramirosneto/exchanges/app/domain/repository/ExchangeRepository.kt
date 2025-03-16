package br.com.ramirosneto.exchanges.app.domain.repository

import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {

    suspend fun getExchanges(): Flow<List<Exchange>>
}
