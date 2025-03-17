package br.com.ramirosneto.exchanges.app.util

import android.annotation.SuppressLint
import br.com.ramirosneto.exchanges.app.data.remote.model.Exchange
import br.com.ramirosneto.exchanges.app.presentation.model.ExchangeDTO

@SuppressLint("DefaultLocale")
fun Exchange.toExchangeDTO(): ExchangeDTO {
    val formattedRate = String.format("%.2f", volume1dayUSD)
    return ExchangeDTO(exchangeId, name, website, formattedRate)
}
