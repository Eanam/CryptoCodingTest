package com.yinan.cryptocodingtest.repository.wallet

import com.google.gson.Gson
import com.yinan.cryptocodingtest.exceptions.NetworkException
import com.yinan.cryptocodingtest.model.entities.WalletAsset
import com.yinan.cryptocodingtest.model.entities.WalletAssetsResponse
import com.yinan.cryptocodingtest.utils.getContentFromAssetJsonFile

class LocalWalletRepository : IWalletRepository {
    override suspend fun getWalletAssets(): Result<List<WalletAsset>> {
        val jsonStr = getContentFromAssetJsonFile("wallet-balance-json.json")
        return try {
            val response = Gson().fromJson(jsonStr, WalletAssetsResponse::class.java)
            if (response.isSuccess) {
                Result.success(response.assets)
            } else {
                Result.failure(NetworkException(response.errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}