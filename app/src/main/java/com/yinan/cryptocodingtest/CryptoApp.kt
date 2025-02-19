package com.yinan.cryptocodingtest

import android.app.Application

class CryptoApp: Application() {

    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}