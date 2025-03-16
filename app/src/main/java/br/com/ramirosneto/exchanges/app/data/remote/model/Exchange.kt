package br.com.ramirosneto.exchanges.app.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Exchange(
    @Json(name = "exchange_id") val exchangeId: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "website") val website: String?,
    @Json(name = "volume_1day_usd") val volume1dayUSD: Double
)
