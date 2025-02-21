package com.yinan.cryptocodingtest.utils

import android.text.SpannableString
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan

fun SpannableString.setColorSpan(color: Int) {
    setSpan(
        ForegroundColorSpan(color),
        0,
        length,
        SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
    )
}

/**
 * @param fontSize unit: sp
 */
fun SpannableString.setFontSize(fontSize: Int) {
    setSpan(
        AbsoluteSizeSpan(fontSize, true),
        0,
        length,
        SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
    )
}