package com.yinan.cryptocodingtest.utils


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yinan.cryptocodingtest.R

fun ImageView.load(url: String) {
    Glide
        .with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.ic_coin_logo_error)
        .into(this)
}