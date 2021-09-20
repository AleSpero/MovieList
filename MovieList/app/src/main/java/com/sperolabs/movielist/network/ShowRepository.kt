package com.sperolabs.movielist.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sperolabs.movielist.home.pagination.ShowPagingSource
import com.sperolabs.movielist.model.DataResult
import com.sperolabs.movielist.model.ShowsResponse
import com.sperolabs.movielist.model.SingleShowResponse
import com.sperolabs.movielist.model.TvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowRepository @Inject constructor(val dataSource: MovieListEndpoint) {

    fun getPopularShows() : Flow<PagingData<TvShow>> =
        Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { ShowPagingSource(dataSource) }
        ).flow

    suspend fun getShowDetail(showId : Int) : DataResult<SingleShowResponse> =
       launchApiCall { dataSource.getShowDetail(showId) }


    suspend fun <T>launchApiCall(apiCall : suspend () -> T) : DataResult<T>{
        return withContext(Dispatchers.Main) {
            try{
                val result = apiCall.invoke()
                 DataResult.Success(result)
            } catch (exception : Throwable){
                DataResult.Failure(exception)
            }
        }

    }

}