package com.yinan.cryptocodingtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yinan.cryptocodingtest.model.WalletAssetWrapper
import com.yinan.cryptocodingtest.model.WalletDetailInfo
import com.yinan.cryptocodingtest.model.entities.CurrencyTier
import com.yinan.cryptocodingtest.model.entities.SupportedCurrency
import com.yinan.cryptocodingtest.model.entities.WalletAsset
import com.yinan.cryptocodingtest.repository.currency.ICurrencyRepository
import com.yinan.cryptocodingtest.repository.currency.LocalCurrencyRepository
import com.yinan.cryptocodingtest.repository.wallet.IWalletRepository
import com.yinan.cryptocodingtest.repository.wallet.LocalWalletRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    sealed class PageState {
        data object INIT : PageState()
        class LoadSuccess(val walletDetailInfo: WalletDetailInfo) : PageState()
        data object LoadFailure : PageState()
    }

    //you can implement ICurrencyRepository for remote request and replace it here
    private val currencyRepo: ICurrencyRepository by lazy { LocalCurrencyRepository() }
    private val walletRepo: IWalletRepository by lazy { LocalWalletRepository() }

    //pageState flow
    private val _pageState = MutableStateFlow<PageState>(PageState.INIT)
    val pageState: StateFlow<PageState> = _pageState

    fun loadData() {
        viewModelScope.launch {
            val assetsResultDeferred = async {
                walletRepo.getWalletAssets()
            }

            val supportedCurrencyResultDeferred = async {
                currencyRepo.getSupportedCurrencyList()
            }

            val liveRateResultDeferred = async {
                currencyRepo.getCurrencyRates()
            }

            val assetsResult = assetsResultDeferred.await()
            val supportedCurrencyResult =
                supportedCurrencyResultDeferred.await()
            val liveRateResult = liveRateResultDeferred.await()

            if (assetsResult.isFailure || supportedCurrencyResult.isFailure || liveRateResult.isFailure) {
                _pageState.tryEmit(PageState.LoadFailure)
                return@launch
            }

            //TODO: we can filter some unsupported currency here for saving time of converting
            val liveRates = liveRateResult.getOrNull().orEmpty()
            //assume that all coin with convert to the same target currency in the wallet page
            val targetCurrency = liveRates.firstOrNull()?.toCurrency
            //transform wallet assets
            val assetWrappers = wrapWalletAssets(
                supportedCurrencyResult.getOrNull().orEmpty().associateBy { it.coinId },
                liveRateResult.getOrNull().orEmpty().associateBy { it.fromCurrency },
                assetsResult.getOrNull().orEmpty()
            )

            _pageState.tryEmit(
                PageState.LoadSuccess(
                    WalletDetailInfo(
                        targetCurrency = targetCurrency,
                        walletAsset = assetWrappers
                    )
                )
            )
        }
    }

    /**
     * calculate the value and compose some required properties of each coin asset
     *
     * @param supportedCurrency contains the information of each coin
     * @param walletAssets the assets of user
     */
    private fun wrapWalletAssets(
        supportedCurrency: Map<String, SupportedCurrency>,
        liveRates: Map<String, CurrencyTier>,
        walletAssets: List<WalletAsset>,
    ): List<WalletAssetWrapper> {
        return walletAssets.mapNotNull {
            supportedCurrency[it.currency]?.let { coinInfo ->
                WalletAssetWrapper(
                    coinId = coinInfo.coinId,
                    coinName = coinInfo.coinName,
                    symbol = coinInfo.coinSymbol,
                    coinLogoUrl = coinInfo.colorfulCoinAvatarUrl,
                    amount = it.currencyAmount,
                    displayDecimal = coinInfo.displayDecimal,
                ).apply { calculateTargetValue(liveRates[it.currency]?.rates) }
            }
        }
    }
}