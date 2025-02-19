package com.yinan.cryptocodingtest.exceptions

class NetworkException(message: String? = null) : Exception(message ?: "unknown network error")