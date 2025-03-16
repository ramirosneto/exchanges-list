package br.com.ramirosneto.exchanges.app.data.remote.api

import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("X-CoinAPI-Key: a2fc229c-4e18-4a19-aa4c-611d04b554dd")
    @GET("v1/exchanges")
    suspend fun getExchanges(): List<Exchange>
}
