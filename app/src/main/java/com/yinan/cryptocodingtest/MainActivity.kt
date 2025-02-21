package com.yinan.cryptocodingtest

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.yinan.cryptocodingtest.model.WalletDetailInfo
import com.yinan.cryptocodingtest.ui.adapters.AssetsAdapter
import com.yinan.cryptocodingtest.utils.isZero
import com.yinan.cryptocodingtest.utils.setColorSpan
import com.yinan.cryptocodingtest.utils.setFontSize
import com.yinan.cryptocodingtest.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    //assets adapter
    private val assetsAdapter by lazy { AssetsAdapter() }

    //views
    private lateinit var totalAmountTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.pageState.collectLatest(this@MainActivity::handlePageState)
            }
        }

        totalAmountTv = findViewById(R.id.tvTotalValue)
        updateTotalValue()

        initRecycleView(findViewById(R.id.assetsRv))
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.loadData()
    }

    private fun initRecycleView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = assetsAdapter
            addItemDecoration(object : ItemDecoration() {
                private val spacing by lazy {
                    CryptoApp.app.resources.getDimension(R.dimen.asset_item_spacing).toInt()
                }

                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = spacing
                }
            })
        }
    }

    private fun handlePageState(state: MainViewModel.PageState) {
        when (state) {
            is MainViewModel.PageState.LoadSuccess -> {
                updateTotalValue(state.walletDetailInfo)
                assetsAdapter.setData(state.walletDetailInfo)
            }

            is MainViewModel.PageState.LoadFailure -> {
                //TODO: display error layout
            }

            else -> { /*do nothing*/
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalValue(detailInfo: WalletDetailInfo? = null) {
        if (!this::totalAmountTv.isInitialized) return
        if (detailInfo == null || detailInfo.totalValue.isZero()) {
            totalAmountTv.text = "0"
            return
        }

        val normalColor = getColor(R.color.wallet_total_value_normal_text_color)
        val highLightColor = getColor(R.color.wallet_total_value_highlight_text_color)
        val spannableBuilder = SpannableStringBuilder()
        detailInfo.apply {
            spannableBuilder.append(
                SpannableString(prefixSymbol).apply {
                    setColorSpan(normalColor)
                }
            )
            spannableBuilder.append(" ")

            spannableBuilder.append(
                totalValue.toPlainString().let {
                    SpannableString(it).apply {
                        setColorSpan(highLightColor)
                        setFontSize(25)
                    }
                }
            )
            spannableBuilder.append(" ")

            targetCurrency?.let {
                SpannableString(it).apply {
                    setColorSpan(normalColor)
                }
            }?.let {
                spannableBuilder.append(it)
                spannableBuilder.append(" ")
            }
        }

        totalAmountTv.text = spannableBuilder
    }
}