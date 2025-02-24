package com.example.pagingv3.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingv3.R
import com.example.pagingv3.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> ()
    private val adapter = TvShowAdapter()
    val pagingAdapter = MyLoadStateAdapter({
        adapter.retry()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        binding.srlMain.setOnRefreshListener {
            adapter.refresh()
            binding.srlMain.isRefreshing = false
        }

        adapter.listener = object : TvShowAdapter.ITvShowCallBack{
            override fun onSelect(id: Int) {
                viewModel.addFavourite(id)
            }
        }

        binding.rvMain.adapter = adapter.withLoadStateFooter(
            footer = pagingAdapter
        )

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                println("state paging = ${it.append}")

            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.flow.collectLatest {
                    println("collect data")
                    adapter.submitData(it)
                }
            }
        }
    }
}