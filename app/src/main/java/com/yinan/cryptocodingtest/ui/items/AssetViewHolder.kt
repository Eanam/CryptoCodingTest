package com.yinan.cryptocodingtest.ui.items

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yinan.cryptocodingtest.R
import com.yinan.cryptocodingtest.model.WalletAssetWrapper
import com.yinan.cryptocodingtest.utils.load

class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivLogo: ImageView by lazy { itemView.findViewById(R.id.ivLogo) }
    private val tvCoinName: TextView by lazy { itemView.findViewById(R.id.tvCoinName) }
    private val tvCoinAmount: TextView by lazy { itemView.findViewById(R.id.tvCoinAmount) }
    private val tvCoinValue: TextView by lazy { itemView.findViewById(R.id.tvCoinValue) }

    @SuppressLint("SetTextI18n")
    fun bindData(prefixSymbol: String?, assetWrapper: WalletAssetWrapper) {
        assetWrapper.apply {
            ivLogo.load(coinLogoUrl)
            tvCoinName.text = coinName
            tvCoinAmount.text = "$displayAmount $symbol"
            tvCoinValue.text =
                if (prefixSymbol == null) "" else "$prefixSymbol  $convertedTargetValue"
        }
    }

}