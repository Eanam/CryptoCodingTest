package com.yinan.cryptocodingtest.repository.currency

import com.google.gson.Gson
import com.yinan.cryptocodingtest.exceptions.NetworkException
import com.yinan.cryptocodingtest.model.entities.CurrencyTier
import com.yinan.cryptocodingtest.model.entities.LiveRateResponse
import com.yinan.cryptocodingtest.model.entities.SupportedCurrency
import com.yinan.cryptocodingtest.model.entities.SupportedCurrencyResponse
import com.yinan.cryptocodingtest.utils.getContentFromAssetJsonFile

/**
 * fetch currency data from local file
 */
class LocalCurrencyRepository : ICurrencyRepository {

    override suspend fun getCurrencyRates(): Result<List<CurrencyTier>> {
        return try {
            val jsonStr = getContentFromAssetJsonFile("live-rates-json.json")
            val response = Gson().fromJson(jsonStr, LiveRateResponse::class.java)
            if (response.isSuccess) {
                Result.success(response.tiers)
            } else {
                Result.failure(NetworkException(response.errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSupportedCurrencyList(): Result<List<SupportedCurrency>> {
        return try {
            val jsonStr = getContentFromAssetJsonFile("currencies-json.json")
            val response = Gson().fromJson(jsonStr, SupportedCurrencyResponse::class.java)
            if (response.isSuccess) {
                Result.success(response.supportedCurrency.orEmpty())
            } else {
                Result.failure(NetworkException())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}