package com.yinan.cryptocodingtest

import android.graphics.Rect
import android.os.Bundle
import android.view.View
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
import com.yinan.cryptocodingtest.ui.adapters.AssetsAdapter
import com.yinan.cryptocodingtest.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    //assets adapter
    private val assetsAdapter by lazy { AssetsAdapter() }

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
                assetsAdapter.setData(state.walletDetailInfo.walletAsset)
            }

            is MainViewModel.PageState.LoadFailure -> {
                //TODO: display error layout
            }

            else -> { /*do nothing*/ }
        }
    }
}