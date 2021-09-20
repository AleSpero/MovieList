package com.sperolabs.movielist.network

import com.sperolabs.movielist.model.ShowsResponse
import com.sperolabs.movielist.model.SingleShowResponse
import com.sperolabs.movielist.model.TvShow
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListEndpoint {

    @GET("api/most-popular/")
    suspend fun getMostPopular(@Query("page") page : Int) : ShowsResponse

    @GET("api/show-details/")
    suspend fun getShowDetail(@Query("q") showId : Int) : SingleShowResponse

    @GET("api/search")
    suspend fun searchForShows(@Query("q") searchPrompt : String, @Query("page") page : Int) : ShowsResponse

}