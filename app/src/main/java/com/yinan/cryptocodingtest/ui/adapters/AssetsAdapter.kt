package com.yinan.cryptocodingtest.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yinan.cryptocodingtest.R
import com.yinan.cryptocodingtest.ui.items.AssetViewHolder

class AssetsAdapter : RecyclerView.Adapter<AssetViewHolder>() {

    private val assets = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(itemView)
    }

    override fun getItemCount() = assets.size

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Any>) {
        assets.clear()
        assets.addAll(newData)
        notifyDataSetChanged()
    }
}