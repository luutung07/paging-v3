package com.example.pagingv3.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pagingv3.data.model.TvShow
import com.example.pagingv3.data.repo.network.TvShowClient
import com.example.pagingv3.data.repo.source.TvShowPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val instanceTvShowServer = TvShowClient.executeTvShow()

    private val _favouriteState = MutableStateFlow<Set<Int>>(hashSetOf())

    private val tvPager = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
    ) {
        TvShowPagingSource(instanceTvShowServer)
    }

    val flow = tvPager.flow
        .cachedIn(viewModelScope)
        .combine(_favouriteState) { a: PagingData<TvShow>, b: Set<Int> ->
            a.map { data ->
                data.copy(isFavourite = b.contains(data.id))
            }
        }

    init {

    }

    fun addFavourite(id: Int) {
        viewModelScope.launch {
            _favouriteState.update {
                if (it.contains(id)) {
                    it - id
                } else {
                    it + id
                }
            }
        }
    }

}