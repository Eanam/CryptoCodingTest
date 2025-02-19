package com.yinan.cryptocodingtest.repository.currency

import com.yinan.cryptocodingtest.model.CurrencyTier
import com.yinan.cryptocodingtest.model.SupportedCurrency

/**
 * the interface defines the request functions attached to currencies
 */
interface ICurrencyRepository {

    suspend fun getCurrencyRates(): Result<List<CurrencyTier>>

    suspend fun getSupportedCurrencyList(): Result<List<SupportedCurrency>>
}