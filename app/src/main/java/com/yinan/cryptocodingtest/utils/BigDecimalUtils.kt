package com.yinan.cryptocodingtest.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0

fun BigDecimal.transformToDisplayUniform(scale: Int = 2): String {
    return setScale(scale, RoundingMode.DOWN)
        .stripTrailingZeros()
        .toPlainString()
}