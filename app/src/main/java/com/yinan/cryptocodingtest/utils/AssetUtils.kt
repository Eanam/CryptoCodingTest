package com.yinan.cryptocodingtest.utils

import com.yinan.cryptocodingtest.CryptoApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getContentFromAssetJsonFile(path: String): String = withContext(Dispatchers.IO) {
    CryptoApp.app.assets.open(path).bufferedReader().use {
        it.readText()
    }
}