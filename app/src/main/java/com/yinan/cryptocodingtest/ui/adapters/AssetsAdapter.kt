package com.yinan.cryptocodingtest.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yinan.cryptocodingtest.R
import com.yinan.cryptocodingtest.model.WalletAssetWrapper
import com.yinan.cryptocodingtest.model.WalletDetailInfo
import com.yinan.cryptocodingtest.ui.items.AssetViewHolder

class AssetsAdapter : RecyclerView.Adapter<AssetViewHolder>() {

    private var walletDetailInfo: WalletDetailInfo? = null
        set(value) {
            field = value
            assets.clear()
            assets.addAll(field?.walletAsset ?: emptyList())
        }
    private var assets = mutableListOf<WalletAssetWrapper>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(itemView)
    }

    override fun getItemCount() = assets.size

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        if (position !in 0 until itemCount) return
        holder.bindData(
            walletDetailInfo?.prefixSymbol,
            assets[position]
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: WalletDetailInfo) {
        walletDetailInfo = newData
        notifyDataSetChanged()
    }
}