package com.sperolabs.movielist

import androidx.paging.PagingSource
import com.sperolabs.movielist.home.pagination.ShowPagingSource
import com.sperolabs.movielist.model.TvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PagingSourceTest {

    val fakeApi = FakeMovieListEndpoint()
    val mockShows = listOf(
        TvShow(1, "TestShow", "2021-09-19", null, "IT", "HBO", "Running",
        "", "", "", 0.0, 0, emptyList()),
        TvShow(2, "TestShow2", "2021-09-16", null, "IT", "HBO", "Running",
            "", "", "", 0.0, 0, emptyList()),
        TvShow(3, "TestShow3", "2021-09-15", null, "FR", "HBO", "Running",
            "", "", "", 0.0, 0, emptyList()),
        TvShow(4, "TestShow4", "2021-09-12", null, "IT", "Netflix", "Running",
            "", "", "", 0.0, 0, emptyList()),
        TvShow(5, "TestShow5", "2021-09-13", null, "US", "HBO", "Running",
            "", "", "", 0.0, 0, emptyList()))

    val pagingSource = ShowPagingSource(fakeApi)

    @Test
    fun pagingSourceCorrectlyLoadsData() = runBlockingTest {

        val expectedPage = PagingSource.LoadResult.Page(
            data = mockShows,
            prevKey = null,
            nextKey = 1
        )

        val actualPage = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 5,
                placeholdersEnabled = false
            )
        )

        assert(expectedPage == actualPage)
    }


}