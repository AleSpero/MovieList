package com.sperolabs.movielist

import com.sperolabs.movielist.home.MainViewModel
import com.sperolabs.movielist.model.DataResult
import com.sperolabs.movielist.network.ShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    val fakeApi = FakeMovieListEndpoint()
    val fakeRepo = ShowRepository(fakeApi)
    val viewModel = MainViewModel(fakeRepo)

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun detailCallLoadingSuccessfully() = testDispatcher.runBlockingTest {
        val detailResult = viewModel.getShowDetail(1).first()
        assert(detailResult is DataResult.Success)
        assert((detailResult as DataResult.Success).response.tvShow.name.isNotEmpty())
    }

}