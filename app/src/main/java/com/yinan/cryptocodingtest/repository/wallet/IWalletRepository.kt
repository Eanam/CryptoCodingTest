package com.yinan.cryptocodingtest.repository.wallet

import com.yinan.cryptocodingtest.model.WalletAsset

interface IWalletRepository {

    suspend fun getWalletAssets(): Result<List<WalletAsset>>

}