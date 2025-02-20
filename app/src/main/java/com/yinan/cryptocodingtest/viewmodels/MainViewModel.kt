package com.yinan.cryptocodingtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yinan.cryptocodingtest.model.WalletDetailInfo
import com.yinan.cryptocodingtest.repository.currency.ICurrencyRepository
import com.yinan.cryptocodingtest.repository.currency.LocalCurrencyRepository
import com.yinan.cryptocodingtest.repository.wallet.IWalletRepository
import com.yinan.cryptocodingtest.repository.wallet.LocalWalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    sealed class PageState {
        data object INIT: PageState()
        class LoadSuccess(val walletDetailInfo: WalletDetailInfo): PageState()
        data object LoadFailure: PageState()
    }

    //you can implement ICurrencyRepository for remote request and replace it here
    private val currencyRepo: ICurrencyRepository by lazy { LocalCurrencyRepository() }
    private val walletRepo: IWalletRepository by lazy { LocalWalletRepository() }

    //pageState flow
    private val _pageState = MutableStateFlow<PageState>(PageState.INIT)
    val pageState: StateFlow<PageState> = _pageState

    fun loadData() {
        viewModelScope.launch {
            val result = walletRepo.getWalletAssets()
            if (result.isFailure) {
                _pageState.tryEmit(PageState.LoadFailure)
                return@launch
            }

            _pageState.tryEmit(
                PageState.LoadSuccess(WalletDetailInfo(result.getOrNull().orEmpty()))
            )
        }
    }
}