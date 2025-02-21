Hi, here is the coding demo from chenyinan, for you convenience，below is some simple introduction about this project.

#### Direct Structure

```plain
src/main
├── AndroidManifest.xml
├── assets //mock data
└── java
    └── com
        └── yinan
            └── cryptocodingtest
                ├── CryptoApp.kt
                ├── MainActivity.kt
                ├── exceptions
                │   └── NetworkException.kt //some custom exception
                ├── model
                │   ├── WalletAssetWrapper.kt  //the converted data model of wallasset
                │   ├── WalletDetailInfo.kt  // the data model composed with the wallet assets and some calculated value
                │   └── entities
                │       ├── CurrencyTier.kt //the entity corresponding to live rate api
                │       ├── SupportedCurrency.kt //the entity corresponding to supported currencies api
                │       └── WalletAssets.kt //the entity corresponding to wallet assets api
                ├── repository
                │   ├── currency // the interface and its implementation attach to currency request
                │   │   ├── ICurrencyRepository.kt
                │   │   └── LocalCurrencyRepository.kt
                │   └── wallet // the interface and its implementation attach to wallet request
                │       ├── IWalletRepository.kt
                │       └── LocalWalletRepository.kt
                ├── ui
                │   ├── adapters
                │   │   └── AssetsAdapter.kt
                │   └── items
                │       └── AssetViewHolder.kt
                ├── utils
                │   ├── AssetUtils.kt // defines some extension functions for operating wallet asset
                │   ├── BigDecimalUtils.kt // define some extension functions for transforming BigDecimal
                │   ├── GlideUtils.kt
                │   └── SpannableUtils.kt
                └── viewmodels
                    └── MainViewModel.kt
```
