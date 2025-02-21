package com.yinan.cryptocodingtest.model

import java.math.BigDecimal

data class WalletDetailInfo(
    //assume that all coin with convert to the same target currency in the wallet page
    //for now it can only be USD
    val targetCurrency: String?,
    //if we want to replace target currency, we should change the prefix here
    val prefixSymbol: String = "$",
    val walletAsset: List<WalletAssetWrapper>
) {

    val totalValue: BigDecimal by lazy {
        var result = BigDecimal(0)
        walletAsset.forEach {
            result += it.convertedTargetValue
        }
        result
    }

}