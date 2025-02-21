package com.yinan.cryptocodingtest.utils

import java.math.BigDecimal

fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0