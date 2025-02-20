package com.yinan.cryptocodingtest.repository.wallet

import com.yinan.cryptocodingtest.model.entities.WalletAsset

interface IWalletRepository {

    suspend fun getWalletAssets(): Result<List<WalletAsset>>

}