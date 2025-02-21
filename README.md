Hi, here is the coding demo from chenyinan, for you convenience，below is some simple introduction about this project.

#### 1. Development Enviromment

- Android Studio: **_Android Studio Ladybug | 2024.2.1 Patch 2_**
- Gradle: **_8.9_**
- AGP: **_8.7.2_**
- Kotlin: **_1.9.34_**

#### 2. Direct Structure

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

#### 3. Some Design<br/>

- ICurrencyRepository & IWalletRepository<br/><br/>
  &nbsp;&nbsp;To facilitate the integration of api requests, MainViewModel is only aware of these interfaces. And we can replace the implementations of them later. <br/><br/>
  &nbsp;&nbsp;As long as the the structure of correponding api response is not modified, we don't need to change other code.
