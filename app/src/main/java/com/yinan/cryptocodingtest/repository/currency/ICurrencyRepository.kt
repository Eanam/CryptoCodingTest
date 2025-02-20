package com.yinan.cryptocodingtest.repository.currency

import com.yinan.cryptocodingtest.model.entities.CurrencyTier
import com.yinan.cryptocodingtest.model.entities.SupportedCurrency

/**
 * the interface defines the request functions attached to currencies
 */
interface ICurrencyRepository {

    suspend fun getCurrencyRates(): Result<List<CurrencyTier>>

    suspend fun getSupportedCurrencyList(): Result<List<SupportedCurrency>>
}