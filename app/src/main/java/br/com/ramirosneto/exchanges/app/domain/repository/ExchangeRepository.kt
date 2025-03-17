package br.com.ramirosneto.exchanges.app.domain.repository

import br.com.ramirosneto.exchanges.app.presentation.model.ExchangeDTO
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {

    suspend fun getExchanges(): Flow<List<ExchangeDTO>>
}
