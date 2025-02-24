package com.example.pagingv3.data.repo.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingv3.data.model.TvShow
import com.example.pagingv3.data.model.TvShowResponse
import com.example.pagingv3.data.repo.network.ITvShowServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext

class TvShowPagingSource(
    private val iServer: ITvShowServer
) : PagingSource<Int, TvShow>() {
    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
            println("refreshing ... $page")
            page
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            withContext(Dispatchers.IO) {
                println("currentKey = ${params.key}")
                val nextPage = params.key ?: 1
                val response = iServer.getTvShowResponse(nextPage).execute()
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!.tvShows

                    kotlinx.coroutines.delay(3000)
                    LoadResult.Page(
                        data = data!!,
                        prevKey = null,
                        nextKey = nextPage + 1
                    )
                } else {
                    val mess = response.errorBody().toString()
                    LoadResult.Error(Exception(mess))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

