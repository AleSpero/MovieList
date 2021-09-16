package com.sperolabs.movielist.home.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sperolabs.movielist.model.DataResult
import com.sperolabs.movielist.model.TvShow
import com.sperolabs.movielist.network.MovieListEndpoint
import java.lang.Exception

class ShowPagingSource(val endpoint: MovieListEndpoint) : PagingSource<Int, TvShow>() {

    companion object{
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val currentPage = params.key ?: FIRST_PAGE

        return try{
            val result = endpoint.getMostPopular(currentPage)
            LoadResult.Page(
                data = result.results,
                nextKey = if(result.results.isEmpty()) null else currentPage + (params.loadSize / PAGE_SIZE),
                prevKey = null
            )

        } catch (exception : Exception){
            LoadResult.Error(exception)
        }


    }
}