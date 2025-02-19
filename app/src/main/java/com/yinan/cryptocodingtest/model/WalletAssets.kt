package com.yinan.cryptocodingtest.model

import com.google.gson.annotations.SerializedName

data class WalletAssetsResponse(
    @SerializedName("ok") val isSuccess: Boolean,
    @SerializedName("warning") val errorMsg: String?,
    @SerializedName("wallet") val assets: List<WalletAsset>
)

data class WalletAsset(
    @SerializedName("currency") val currency: String,
    @SerializedName("amount") val currencyAmount: String
)