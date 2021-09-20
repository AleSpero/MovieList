package com.sperolabs.movielist

import com.sperolabs.movielist.model.ShowsResponse
import com.sperolabs.movielist.model.SingleShowResponse
import com.sperolabs.movielist.model.TvShow
import com.sperolabs.movielist.network.MovieListEndpoint

class FakeMovieListEndpoint : MovieListEndpoint {
    override suspend fun getMostPopular(page: Int): ShowsResponse =
        ShowsResponse(1, 1, listOf(
            TvShow(1, "TestShow", "2021-09-19", null, "IT", "HBO", "Running",
                "", "", "", 0.0, 0, emptyList()),
            TvShow(2, "TestShow2", "2021-09-16", null, "IT", "HBO", "Running",
                "", "", "", 0.0, 0, emptyList()),
            TvShow(3, "TestShow3", "2021-09-15", null, "FR", "HBO", "Running",
                "", "", "", 0.0, 0, emptyList()),
            TvShow(4, "TestShow4", "2021-09-12", null, "IT", "Netflix", "Running",
                "", "", "", 0.0, 0, emptyList()),
            TvShow(5, "TestShow5", "2021-09-13", null, "US", "HBO", "Running",
                "", "", "", 0.0, 0, emptyList()),
        ))

    override suspend fun getShowDetail(showId: Int): SingleShowResponse =
        SingleShowResponse(
            TvShow(1, "TestShow", "2021-09-19", null, "IT", "HBO", "Running",
            "", "", "", 0.0, 0, emptyList())
        )

}