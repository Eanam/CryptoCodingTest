package com.yinan.cryptocodingtest.model.entities

import com.google.gson.annotations.SerializedName

data class SupportedCurrencyResponse(
    @SerializedName("ok") val isSuccess: Boolean,
    @SerializedName("total") val totalSupported: Int?,
    @SerializedName("currencies") val supportedCurrency: List<SupportedCurrency>?
)

class SupportedCurrency(
    @SerializedName("coin_id") val coinId: String,
    @SerializedName("name") val coinName: String,
    @SerializedName("symbol") val coinSymbol: String,
    @SerializedName("colorful_image_url") val colorfulCoinAvatarUrl: String,
)