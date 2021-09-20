package com.sperolabs.movielist

import com.sperolabs.movielist.model.ShowsResponse
import com.sperolabs.movielist.model.SingleShowResponse
import com.sperolabs.movielist.network.MovieListEndpoint
import java.lang.IllegalArgumentException

class ErrorEndpoint : MovieListEndpoint {
    override suspend fun getMostPopular(page: Int): ShowsResponse {
        throw IllegalArgumentException("This is the error message")
    }

    override suspend fun getShowDetail(showId: Int): SingleShowResponse {
        throw IllegalArgumentException("This is the error message")
    }
}