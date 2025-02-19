package com.yinan.cryptocodingtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yinan.cryptocodingtest.repository.currency.ICurrencyRepository
import com.yinan.cryptocodingtest.repository.currency.LocalCurrencyRepository
import com.yinan.cryptocodingtest.repository.wallet.IWalletRepository
import com.yinan.cryptocodingtest.repository.wallet.LocalWalletRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //you can implement ICurrencyRepository for remote request and replace it here
    private val currencyRepo: ICurrencyRepository by lazy { LocalCurrencyRepository() }
    private val walletRepo: IWalletRepository by lazy { LocalWalletRepository() }

    fun loadData() {
        viewModelScope.launch {
            val result = currencyRepo.getSupportedCurrencyList()
        }
    }
}