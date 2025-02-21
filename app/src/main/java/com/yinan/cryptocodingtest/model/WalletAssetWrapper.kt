package com.yinan.cryptocodingtest.model

import com.yinan.cryptocodingtest.model.entities.CurrencyRate
import com.yinan.cryptocodingtest.model.entities.CurrencyTier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * compose the properties of each asset of user
 */
data class WalletAssetWrapper(
    val coinId: String,
    val coinName: String,
    val symbol: String,
    val coinLogoUrl: String,
    val amount: String,
    val displayDecimal: Int,
) {

    private val amountDecimal = BigDecimal(amount)
    var convertedTargetValue = BigDecimal(0)
        private set

    val displayAmount: String by lazy {
        amountDecimal
            .setScale(displayDecimal, RoundingMode.DOWN)
            .stripTrailingZeros()
            .toPlainString()
    }


    fun calculateTargetValue(liveRates: List<CurrencyRate>?) {
        if (liveRates.isNullOrEmpty()) return
        /**
         *  not sure about the use of field 'amount',
         *  so i use the first to calculate the value for now
         *
         *         if (liveRates.size == 1) {
         *             calculateAndSetTargetValue(liveRates.first().rate)
         *             return
         *         }
         *         withContext(Dispatchers.Default) {
         *             val sortedRates = liveRates.sortedBy { it.amount }
         *             //binary search
         *
         *         }
         */
        calculateAndSetTargetValue(liveRates.firstOrNull()?.rate)
    }

    private fun calculateAndSetTargetValue(rate: String?) {
        if (rate == null) {
            convertedTargetValue = BigDecimal(0)
            return
        }

        convertedTargetValue = kotlin.runCatching {
            BigDecimal(rate) * BigDecimal(amount)
        }.getOrNull() ?: BigDecimal(0)
    }
}