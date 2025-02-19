package com.yinan.cryptocodingtest.model

import com.google.gson.annotations.SerializedName

/**
 * response wrapper of live rate api
 */
data class LiveRateResponse(
    @SerializedName("ok") val isSuccess: Boolean,
    @SerializedName("warning") val errorMsg: String?,
    @SerializedName("tiers") val tiers: List<CurrencyTier>
)

/**
 * the data model of the exchange rate between currencies
 *
 * @param fromCurrency source currency
 * @param toCurrency target currency
 * @param rates different rates between these two currencies
 */
data class CurrencyTier(
    @SerializedName("from_currency") val fromCurrency: String,
    @SerializedName("to_currency") val toCurrency: String,
    @SerializedName("rates") val rates: List<CurrencyRate>
)

/**
 * the data model of rate
 *
 * @param amount the minimum amount to be exchanged at this rate?
 * @param rate the exchange rate
 */
data class CurrencyRate(
    @SerializedName("amount") val amount: String,
    @SerializedName("rate") val rate: String,
)